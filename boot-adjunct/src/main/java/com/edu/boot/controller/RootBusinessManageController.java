package com.edu.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.boot.common.api.CommonResult;
import com.edu.boot.entity.*;
import com.edu.boot.entity.Vo.*;
import com.edu.boot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Controller
public class RootBusinessManageController {
    @Autowired
    UserService merchantsUserService;
    @Autowired
    RecruitmentService recruitmentService;
    @Autowired
    BusinessShopService businessShopService;
    @Autowired
    UserService stuManageService;


    /**
     * 商家管理列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("business/show")
    public CommonResult merchantsShow(@RequestParam(name = "currentpage") Integer current, @RequestParam(name = "pagesize") Integer size) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uid","username","realname","image_data","identity_image","create_time","mobile","email","age","pid","identification").eq("pid",3);
        IPage<Users> list = merchantsUserService.page(new Page<>(current, size), queryWrapper);
        if (list.getRecords().isEmpty()) return CommonResult.failed("获取商家列表失败！！");
        return CommonResult.success(list, "获取商家列表成功！！");
    }

    @Autowired
    NotesManageService notesManageService;
    @Autowired
    SResumeService sResumeService;

    /**
     * 商店管理列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("shop/show")
    public CommonResult shopShow(@RequestParam(name = "currentpage") Integer current, @RequestParam(name = "pagesize") Integer size) {
        Page<BusinessShopAllInfoVo> page = new Page<>(current, size);
        IPage<BusinessShopAllInfoVo> businessShopInfo = businessShopService.getBusinessShopInfo(page);
        for (BusinessShopAllInfoVo businessShopAllInfoVo : businessShopInfo.getRecords()) {
            QueryWrapper<Recruitment> recruitmentQueryWrapper = new QueryWrapper<>();
            Integer bId = businessShopAllInfoVo.getBId();
            recruitmentQueryWrapper.eq("b_id", bId);
            //这商店的所有招聘
            List<Recruitment> list = recruitmentService.list(recruitmentQueryWrapper);
            List<RecruitmentAndSonNotesManageVo> recruitmentAndSonNotesManageVos = new ArrayList<>();
            //遍历招聘获取 每个招聘收到的简历
            for (Recruitment o : list) {

                final RecruitmentAndSonNotesManageVo recruitmentAndSonNotesManageVo = new RecruitmentAndSonNotesManageVo();
                recruitmentAndSonNotesManageVo.setRecStation(o.getRecStation());
                recruitmentAndSonNotesManageVo.setRecId(o.getRecId());
                recruitmentAndSonNotesManageVo.setRecHeat(o.getRecHeat());
                recruitmentAndSonNotesManageVo.setRecObj(o.getRecObj());
                recruitmentAndSonNotesManageVo.setRecSalary(o.getRecSalary());
                recruitmentAndSonNotesManageVo.setRecInfo(o.getRecInfo());
                QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();

                queryWrapper.eq("rec_id", o.getRecId());
                //这招聘下的一系列简历
                List<NotesManage> list1 = notesManageService.list(queryWrapper);
                List<SResumeAndNid> sResumes = new ArrayList<>();
                for (NotesManage notesManage : list1) {

                    List<SResumeAndNid> allNotesByTid = sResumeService.getAllNotesByTid(notesManage.getTId(),notesManage.getNId());
                    //获取到所有的这个招聘的简历，然后存储
                    sResumes.addAll(allNotesByTid);
                    recruitmentAndSonNotesManageVo.setChildren(sResumes);
                }
                recruitmentAndSonNotesManageVos.add(recruitmentAndSonNotesManageVo);
            }
            businessShopAllInfoVo.setChildren(recruitmentAndSonNotesManageVos);
        }

        if (businessShopInfo.getRecords().isEmpty()) return CommonResult.failed("获取商家列表失败！！");
        return CommonResult.success(businessShopInfo, "获取商家列表成功！！");
    }


    /**
     * 招聘管理列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("recruitment/show")
    public CommonResult recruitmentShow() {
        QueryWrapper<Recruitment> recruitmentQueryWrapper = new QueryWrapper<>();

        recruitmentQueryWrapper.select("rec_id", "rec_station", "rec_salary", "rec_heat", "rec_info", "rec_obj");
        List<Recruitment> list = recruitmentService.list(recruitmentQueryWrapper);
        if (list.isEmpty()) return CommonResult.failed("获取商家列表失败！！");
        return CommonResult.success(list, "获取商家列表成功！！");
    }


    /**
     * 商家用户名搜索
     */
    @ResponseBody
    @GetMapping("business/search")
    public CommonResult merchantsSearchByName(@RequestParam(name = "currentpage") Integer current, @RequestParam(name = "pagesize") Integer size, @RequestParam(name = "search") String search) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.select("uid","username","realname","image_data","identity_image","create_time","mobile","email","age","pid","identification").like("realname", search).eq("pid",3);
        IPage<Users> list = merchantsUserService.page(new Page<>(current, size), wrapper);
        if (list.getRecords().isEmpty()) return CommonResult.failed("搜索失败！！");
        return CommonResult.success(list, "搜索成功！！");
    }

    /**
     * 商店名字搜索
     */
    @ResponseBody
    @GetMapping("shop/search")
    public CommonResult shopSearchByName(@RequestParam(name = "currentpage") Integer current, @RequestParam(name = "pagesize") Integer size, @RequestParam(name = "search") String search) {
        Page<BusinessShopAllInfoVo> page = new Page<>(current, size);
        IPage<BusinessShopAllInfoVo> businessShopInfoByLikeName = businessShopService.getBusinessShopInfoByLikeName(page, "%" + search + "%");

        for (BusinessShopAllInfoVo businessShopAllInfoVo : businessShopInfoByLikeName.getRecords()) {
            QueryWrapper<Recruitment> recruitmentQueryWrapper = new QueryWrapper<>();
            Integer bId = businessShopAllInfoVo.getBId();
            recruitmentQueryWrapper.eq("b_id", bId);
            //这商店的所有招聘
            List<Recruitment> list = recruitmentService.list(recruitmentQueryWrapper);
            List<RecruitmentAndSonNotesManageVo> recruitmentAndSonNotesManageVos = new ArrayList<>();
            //遍历招聘获取 每个招聘收到的简历
            for (Recruitment o : list) {

                final RecruitmentAndSonNotesManageVo recruitmentAndSonNotesManageVo = new RecruitmentAndSonNotesManageVo();
                recruitmentAndSonNotesManageVo.setRecStation(o.getRecStation());
                recruitmentAndSonNotesManageVo.setRecId(o.getRecId());
                recruitmentAndSonNotesManageVo.setRecHeat(o.getRecHeat());
                recruitmentAndSonNotesManageVo.setRecObj(o.getRecObj());
                recruitmentAndSonNotesManageVo.setRecSalary(o.getRecSalary());
                recruitmentAndSonNotesManageVo.setRecInfo(o.getRecInfo());
                QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("rec_id", o.getRecId());
                //一系列简历
                List<NotesManage> list1 = notesManageService.list(queryWrapper);
                List<SResumeAndNid> sResumes  = new ArrayList<>();;
                for (NotesManage notesManage : list1) {
                    List<SResumeAndNid> allNotesByTid = sResumeService.getAllNotesByTid(notesManage.getTId(),notesManage.getNId());
                    //获取到所有的这个招聘的简历，然后存储
                    sResumes.addAll(allNotesByTid);
                }
                recruitmentAndSonNotesManageVo.setChildren(sResumes);
                recruitmentAndSonNotesManageVos.add(recruitmentAndSonNotesManageVo);
            }
            businessShopAllInfoVo.setChildren(recruitmentAndSonNotesManageVos);
        }

        if (businessShopInfoByLikeName.getRecords().isEmpty()) return CommonResult.failed("获取商家列表失败！！");
        return CommonResult.success(businessShopInfoByLikeName, "获取商家列表成功！！");
    }


    /**
     * 通过ID修改商家信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping("business/edit")
    public CommonResult merchantsEditById(@RequestBody Users users) {
        users.setCreateTime(null);
        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("uid",users.getUid());
        usersUpdateWrapper.set("username",users.getUsername());
        usersUpdateWrapper.set("realname",users.getRealname());
        usersUpdateWrapper.set("image_data",users.getImageData());
        usersUpdateWrapper.set("mobile",users.getMobile());
        usersUpdateWrapper.set("email",users.getEmail());
        usersUpdateWrapper.set("age",users.getAge());
        usersUpdateWrapper.set("pid",users.getPid());
        usersUpdateWrapper.set("identification",users.getIdentification());
        boolean update = stuManageService.update(users, usersUpdateWrapper);
        if (!update) return CommonResult.failed("修改失败！！");
        return CommonResult.success(null, "修改成功！！");
    }

    /*通过ID认证身份*/
    @ResponseBody
    @GetMapping("business/businessIdentity")
    public CommonResult identityPassById(@RequestParam(name = "currentpage") Integer current,@RequestParam(name = "pagesize") Integer size){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uid","username","identity_image","pid","identification").eq("pid", 3);
        IPage<Users> page = stuManageService.page(new Page<>(current, size), queryWrapper);
        if (page.getRecords().isEmpty()) return CommonResult.failed("获取商家列表失败！！");
        return CommonResult.success(page,"获取商家列表成功！！");
    }

    /**
     * 通过ID删除商家信息
     * 删除这商家的商店、招聘、简历管理、轮播图、头条、评价
     * @return
     */
    @ResponseBody
    @GetMapping("business/del/{uid}")
    public CommonResult merchantsDeleteById(@PathVariable Integer uid) {
        //搜索商家uid的所有b_id招聘用的
        QueryWrapper<BusinessShop> businessShopQueryWrapper1 = new QueryWrapper<>();
        //搜索商家uid用的
        businessShopQueryWrapper1.eq("uid", uid);
        //这商家的所有商店
        List<BusinessShop> buslist = businessShopService.list(businessShopQueryWrapper1);
        for (BusinessShop businessShop : buslist) {
            //遍历商店的每个招聘
            QueryWrapper<Recruitment> recruitmentQueryWrapper = new QueryWrapper<>();
            recruitmentQueryWrapper.eq("b_id", businessShop.getBId());
            final List<Recruitment> list = recruitmentService.list(recruitmentQueryWrapper);
            for (Recruitment recruitment : list) {
                //某个简历的头条全删
                //某个简历的轮播图全删
                final QueryWrapper<Banner> bannerQueryWrapper = new QueryWrapper<>();
                bannerQueryWrapper.eq("rec_id",recruitment.getRecId());
                bannerService.remove(bannerQueryWrapper);
                //某个招聘收到的简历也删除
                final QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("rec_id",recruitment.getRecId());
                notesManageService.remove(queryWrapper);
                //删除评价信息
                QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
                commentQueryWrapper.eq("rec_id",recruitment.getRecId());
                for (Comment comment : commentService.list(commentQueryWrapper)) {
                    QueryWrapper<CommentReply> commentReplyQueryWrapper = new QueryWrapper<>();
                    commentReplyQueryWrapper.eq("answer_id",comment.getComId());
                    commentReplyService.remove(commentReplyQueryWrapper);
                }
                commentService.remove(commentQueryWrapper);
            }
            //再删除他们的招聘信息
            recruitmentService.remove(recruitmentQueryWrapper);
        }
        //在删除这用户所有商店 所有带uid
        businessShopService.remove(businessShopQueryWrapper1);
        //再删除这用户

        final boolean remove = merchantsUserService.removeById(uid);

        if (!remove) return CommonResult.failed("删除失败！！");

        return CommonResult.success(null, "删除成功！！");

    }


    /**
     * 通过ID修改商店信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping("shop/edit")
    public CommonResult shopEditById(@RequestBody BusinessShopAllInfoVo businessShop) {
        UpdateWrapper<BusinessShop> businessShopUpdateWrapper = new UpdateWrapper<>();
        businessShopUpdateWrapper.eq("b_id", businessShop.getBId());
        businessShopUpdateWrapper.set("b_name", businessShop.getBName());
        businessShopUpdateWrapper.set("b_head", businessShop.getBHead());
        businessShopUpdateWrapper.set("b_address", businessShop.getBAddress());
        businessShopUpdateWrapper.set("b_image", businessShop.getBImage());
        businessShopUpdateWrapper.set("b_identity",businessShop.getBIdentity());
        businessShopUpdateWrapper.set("b_identity_image",businessShop.getBIdentityImage());
        boolean update = businessShopService.update(businessShopUpdateWrapper);
        if (!update) return CommonResult.failed("操作失败！！");
        return CommonResult.success(null, "操作成功！！");
    }

    @Autowired
    TopService topService;
    @Autowired
    BannerService bannerService;

    /**
     * 通过ID删除商店信息
     *
     * @return
     */
    @ResponseBody
    @GetMapping("shop/del/{bid}")
    public CommonResult shopDeleteById(@PathVariable Integer bid) {
        System.out.println(bid);
        //先通过bid获取这店铺所有的招聘信息recId，再通过recId获取他们的头条和轮播图，全部删除
        final QueryWrapper<Recruitment> recruitmentQueryWrapper = new QueryWrapper<>();
        recruitmentQueryWrapper.eq("b_id",bid);
        final List<Recruitment> list = recruitmentService.list(recruitmentQueryWrapper);
        System.out.println(list);

        //遍历得到所有商店b_id的招聘
        for (Recruitment recruitment : list) {
            //某个简历的头条全删
            //某个简历的轮播图全删
            final QueryWrapper<Banner> bannerQueryWrapper = new QueryWrapper<>();
            bannerQueryWrapper.eq("rec_id",recruitment.getRecId());
            bannerService.remove(bannerQueryWrapper);
            //某个招聘收到的简历也删除
            final QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("rec_id",recruitment.getRecId());
            notesManageService.remove(queryWrapper);
            //删除评价信息
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("rec_id",recruitment.getRecId());
            for (Comment comment : commentService.list(commentQueryWrapper)) {
                QueryWrapper<CommentReply> commentReplyQueryWrapper = new QueryWrapper<>();
                commentReplyQueryWrapper.eq("answer_id",comment.getComId());
                commentReplyService.remove(commentReplyQueryWrapper);
            }
            commentService.remove(commentQueryWrapper);
        }
        //再删除他们的招聘信息
        recruitmentService.remove(recruitmentQueryWrapper);
        //在删除这个商店bid
        final boolean remove1 = businessShopService.remove(new QueryWrapper<BusinessShop>().eq("b_id", bid));
        if (!remove1) return CommonResult.failed("删除失败！！");
        return CommonResult.success(null, "删除成功！！");
    }

    /**
     * 管理员通过nid删除某个招聘下的某个简历
     */
    @ResponseBody
    @PostMapping("rec/notes/del")
    public CommonResult delNotesByNid(@RequestParam(name = "nId") Integer nId) {
        QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("n_id", nId);
        boolean remove = notesManageService.remove(queryWrapper);
        if (!remove) return CommonResult.failed("删除失败！！");

        return CommonResult.success(null, "删除成功！！");
    }


    @Autowired
    CommentService commentService;
    @Autowired
    CommentReplyService commentReplyService;

    /**
     * 获取所有招聘信息下的评论信息
     */
    @ResponseBody
    @GetMapping("rec/comment")
    public CommonResult getAllRecAndComment() {
        final List<CommentChildrenCommentReply> list = commentService.getAllCommentAndReply();
        for (CommentChildrenCommentReply comment : list) {
            final QueryWrapper<CommentReply> commentReplyQueryWrapper = new QueryWrapper<>();
            commentReplyQueryWrapper.eq("answer_id",comment.getComId());
            final List<CommentReply> list1 = commentReplyService.list(commentReplyQueryWrapper);
            comment.setChildren(list1);
        }
        if (list.isEmpty()) return CommonResult.failed("获取失败！！");
        return CommonResult.success(list, "获取成功！！");
    }

    /*展示未认证商店*/
    @ResponseBody
    @GetMapping("shop/shopIdentity")
    public CommonResult identityPassShop(@RequestParam(name = "currentpage") Integer current,@RequestParam(name = "pagesize") Integer size){
        QueryWrapper<BusinessShopAllInfoVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("b_id","uid","b_name","b_head","b_identity_image","b_identity").eq("pid", 2);
        IPage<BusinessShopAllInfoVo> page = businessShopService.getBusinessShopIdentity(new Page<>(current, size), queryWrapper);
        if (page.getRecords().isEmpty()) return CommonResult.failed("获取商店列表失败！！");
        return CommonResult.success(page,"获取商店列表成功！！");
    }

}
