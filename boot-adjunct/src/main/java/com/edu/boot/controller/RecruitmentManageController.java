package com.edu.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.edu.boot.common.api.CommonResult;
import com.edu.boot.entity.*;
import com.edu.boot.entity.Vo.RecruitmentAndSonNotesManageVo;
import com.edu.boot.entity.Vo.SResumeAndNid;
import com.edu.boot.entity.Vo.ShopAndRecruitmentVo;
import com.edu.boot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RecruitmentManageController {

    @Autowired
    RecruitmentService recruitmentService;
    @Autowired
    BannerService bannerService;

    /**
     * 通过店铺id获取招聘信息
     */
    @ResponseBody
    @GetMapping("managehome/shop/recruitment/{id}")
    public CommonResult shopRecruitmentShow(@PathVariable Integer id) {
        List<ShopAndRecruitmentVo> list = recruitmentService.getShopAndRecruitmentInfoByBid(id);
        if (list.isEmpty()) {
            return CommonResult.failed("该商家没有招聘信息，请添加！");
        }
        return CommonResult.success(list,"获取招聘信息成功！");
    }

    /**
     * 新增招聘信息
     */
    @ResponseBody
    @PostMapping("managehome/shop/recruitment/add")
    public CommonResult addRecruitment(@RequestBody Recruitment recruitment) {
        boolean save = recruitmentService.save(recruitment);
        if (save) return CommonResult.success(null,"添加招聘信息成功！");
        return CommonResult.failed("添加招聘信息失败！！");
    }
    /**
     * 修改招聘信息
     */
    @ResponseBody
    @PostMapping("managehome/shop/recruitment/edit")
    public CommonResult editRecruitment(@RequestBody Recruitment recruitment) {
        System.out.println(recruitment);
        if (recruitment == null) CommonResult.failed("修改失败！");
        UpdateWrapper<Recruitment> recruitmentUpdateWrapper = new UpdateWrapper<>();

        recruitmentUpdateWrapper.eq("rec_id", recruitment.getRecId());
        recruitmentUpdateWrapper.set("rec_salary",recruitment.getRecSalary());
        recruitmentUpdateWrapper.set("rec_station",recruitment.getRecStation());
        recruitmentUpdateWrapper.set("rec_info",recruitment.getRecInfo());
        recruitmentUpdateWrapper.set("rec_obj",recruitment.getRecObj());

        boolean update = recruitmentService.update(recruitmentUpdateWrapper);
        System.out.println(update);
        if (update) return CommonResult.success(null,"修改招聘信息成功！");
        return CommonResult.failed("修改招聘信息失败！");
    }

    @Autowired
    CommentService commentService;
    @Autowired
    CommentReplyService commentReplyService;
    @Autowired
    TopService topService;
    /**
     * 删除招聘信息 recId
     * 删除他的头条 和 轮播图 和 收到的简历、评论 再删除这招聘
     */
    @ResponseBody
    @GetMapping("managehome/shop/recruitment/del/{id}")
    public CommonResult delRecruitmentShow(@PathVariable Integer id) {
        //某个简历的头条全删
        //某个简历的轮播图全删
        final QueryWrapper<Banner> bannerQueryWrapper = new QueryWrapper<>();
        bannerQueryWrapper.eq("rec_id",id);
        bannerService.remove(bannerQueryWrapper);
        //某个招聘收到的简历也删除
        final QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rec_id",id);
        notesManageService.remove(queryWrapper);

        //删除评价信息
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("rec_id",id);
        for (Comment comment : commentService.list(commentQueryWrapper)) {
            QueryWrapper<CommentReply> commentReplyQueryWrapper = new QueryWrapper<>();
            commentReplyQueryWrapper.eq("answer_id",comment.getComId());
            commentReplyService.remove(commentReplyQueryWrapper);
        }
        commentService.remove(commentQueryWrapper);
        //删除这简历
        final QueryWrapper<Recruitment> recruitmentQueryWrapper = new QueryWrapper<>();
        recruitmentQueryWrapper.eq("rec_id",id);
        final boolean remove = recruitmentService.remove(recruitmentQueryWrapper);
        if (!remove) return CommonResult.failed("删除失败！！");
        return CommonResult.success(null, "删除成功！！");
    }

    /**
     * 海报标题操作
     */
    @ResponseBody
    @GetMapping("recruitment/poster/exist/{rid}")
    public CommonResult RecruitmentPosterExist(@PathVariable Integer rid) {
        QueryWrapper<Banner> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("rec_id", rid);
        Banner one = bannerService.getOne(wrapper1);
        if (one == null) return CommonResult.failed("当前没有海报，请添加！");
        return CommonResult.success(one,"获取当前海报成功！");
    }

    @ResponseBody
    @PostMapping ("recruitment/poster/add")
    public CommonResult RecruitmentPoster(@RequestBody Banner banner) {
        UpdateWrapper<Banner> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("rec_id", banner.getRecId());
        Banner one = bannerService.getOne(wrapper1);



        //存在 修改图片 和 标题
        if (one != null) {
            wrapper1.set("top_title",banner.getTopTitle());
            wrapper1.set("rot_image",banner.getRotImage());
            boolean update = bannerService.update(wrapper1);
            if (!update) return CommonResult.failed("修改头条失败！");
            return CommonResult.success(null,"修改头条成功！");
        }
        //不存在 直接增加序列
        banner.setBannerUrl("/suthome/recshow/"+banner.getRecId());
        boolean save = bannerService.save(banner);
        if (!save) return  CommonResult.failed("添加失败！");

        return CommonResult.success(banner,"添加成功！");
    }


    @Autowired
    NotesManageService notesManageService;
    @Autowired
    SResumeService sResumeService;
    /**
     * 商家获取学生投递的简历 nId uid tId recId
     * 1.商家通过招聘信息来划分
     * 2.查看所有我的bossid 获取到我的所有招聘recid，查看这个招聘有没有学生发简历过来
     */
    @ResponseBody
    @GetMapping("managehome/rec/notes/{id}")
    public CommonResult getMyAllNotes(@PathVariable Integer id) {
        QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("boss_id",id);
        List<NotesManage> list1 = notesManageService.list(queryWrapper);
        HashMap<Integer,ArrayList<SResumeAndNid>> map = new HashMap<>();
        List<RecruitmentAndSonNotesManageVo> recruitmentAndSonNotesManageVos = new ArrayList<>();

        //遍历所有这老板旗下的收到的简历
        for (NotesManage notesManage : list1) {
            //如果Map集合中recid不存在就创建recid 指向 这招聘信息的所有简历
            if(!map.containsKey(notesManage.getRecId())){
                //获取这次不重复招聘信息 的 学生简历
                SResumeAndNid sResume = sResumeService.getOneTid(notesManage.getTId());
                sResume.setNId(notesManage.getNId());
                ArrayList<SResumeAndNid> sResumeLists = new ArrayList<>();
                sResume.setNId(notesManage.getNId());
                sResume.setState(notesManage.getState());
                sResume.setNotesInfo(notesManage.getNotesInfo());
                sResumeLists.add(sResume);
                //存放到map中
                map.put(notesManage.getRecId(),sResumeLists);
            }else {
                //如果map中已经存在这简历了就要获取这map的学生简历，然后追加新的学生简历，再重置这集合
                ArrayList<SResumeAndNid> sResumes = map.get(notesManage.getRecId());
                ArrayList<SResumeAndNid> newsResumes = new ArrayList<>();
                SResumeAndNid sResume = sResumeService.getOneTid(notesManage.getTId());
                sResume.setNId(notesManage.getNId());
                sResume.setNId(notesManage.getNId());
                sResume.setState(notesManage.getState());
                sResume.setNotesInfo(notesManage.getNotesInfo());
                newsResumes.addAll(sResumes);
                newsResumes.add(sResume);
                map.put(notesManage.getRecId(),newsResumes);
            }
        }

        RecruitmentAndSonNotesManageVo recruitmentAndSonNotesManageVo = null;
        for (Object o : map.keySet().toArray()) {
            recruitmentAndSonNotesManageVo = new RecruitmentAndSonNotesManageVo();
            QueryWrapper<Recruitment> recruitmentQueryWrapper = new QueryWrapper<>();
            recruitmentQueryWrapper.eq("rec_id",(Integer) o);
            final Recruitment byId = recruitmentService.getOne(recruitmentQueryWrapper);
            recruitmentAndSonNotesManageVo.setRecSalary(byId.getRecSalary());
            recruitmentAndSonNotesManageVo.setRecInfo(byId.getRecInfo());
            recruitmentAndSonNotesManageVo.setRecId(byId.getRecId());
            recruitmentAndSonNotesManageVo.setRecHeat(byId.getRecHeat());
            recruitmentAndSonNotesManageVo.setRecObj(byId.getRecObj());

            recruitmentAndSonNotesManageVo.setRecStation(byId.getRecStation());

            ArrayList<SResumeAndNid> sResumes = new ArrayList<>();
            sResumes.addAll(map.get(o));
            recruitmentAndSonNotesManageVo.setChildren(sResumes);

            recruitmentAndSonNotesManageVos.add(recruitmentAndSonNotesManageVo);
        }
        return CommonResult.success(recruitmentAndSonNotesManageVos,"获取各个招聘信息的简历成功！！");
    }

}
