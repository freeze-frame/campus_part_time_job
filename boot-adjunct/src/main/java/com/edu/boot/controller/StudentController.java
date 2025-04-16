package com.edu.boot.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.edu.boot.common.MD5Utils;
import com.edu.boot.common.UUIDUtils;
import com.edu.boot.common.api.CommonResult;
import com.edu.boot.entity.*;
import com.edu.boot.entity.Vo.*;
import com.edu.boot.mapper.NotesManageMapper;
import com.edu.boot.service.CommentReplyService;
import com.edu.boot.service.CommentService;
import com.edu.boot.service.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
public class StudentController {
    @Autowired
    UserService stuUserService;
    @Autowired
    RecruitmentService recruitmentService;
    @Autowired
    BusinessShopService businessShopService;
    @Autowired
    BannerService bannerService;
    @Autowired
    SResumeService sResumeService;

    @Value("${ip:localhost}")
    String ip;
    @Value("${server.port}")
    String port;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    /**
     * 获取所有简历 以及 主人-商店详细
     *
     * @return
     */
    @ResponseBody
    @GetMapping("suthome/stuindex/getrec")
    public CommonResult getRec() {
        List<RecruitmentAllInfoVo> userAllInfo = recruitmentService.getUserAllInfo();
        if (userAllInfo.isEmpty()) return CommonResult.failed("获取招聘信息失败！");
        return CommonResult.success(userAllInfo, "获取招聘信息成功！");
    }

    /**
     * 通过简历rid获取所有这个简历 主人-商店详细信息
     */
    @ResponseBody
    @GetMapping("suthome/stuindex/recshow/{id}")
    public CommonResult getRecInfoByid(@PathVariable Integer id) {
        RecruitmentAllInfoVo userAllShopRecruitmentInfoByRid = recruitmentService.getUserAllShopRecruitmentInfoByRid(id);
        if (userAllShopRecruitmentInfoByRid == null) return CommonResult.failed("获取招聘详细信息失败！");
        return CommonResult.success(userAllShopRecruitmentInfoByRid, "获取招聘详细信息成功！");
    }

    /**
     * 搜索职位关键字返回前端 所有信息
     */
    @ResponseBody
    @GetMapping("suthome/stuindex/search/{search}")
    public CommonResult searchRecInfo(@PathVariable String search) {
        List<RecruitmentAllInfoVo> userAllInfoSearch = recruitmentService.getUserAllInfoSearch("%" + search + "%");
        if (userAllInfoSearch.isEmpty()) return CommonResult.failed("未搜索到关键字信息！");
        return CommonResult.success(userAllInfoSearch, "搜索成功！");
    }


    /**
     * 前端点击访问某个招聘页面，就给这个招聘信息热度+1
     */
    @ResponseBody
    @GetMapping("suthome/stuindex/addheat/{rid}")
    public CommonResult AddRecHeat(@PathVariable Integer rid) {
        UpdateWrapper<Recruitment> recruitmentUpdateWrapper = new UpdateWrapper<>();
        recruitmentUpdateWrapper.eq("rec_id", rid);
        recruitmentUpdateWrapper.setSql("`rec_heat` = `rec_heat` + 1");
        boolean update = recruitmentService.update(recruitmentUpdateWrapper);
        if (!update) return CommonResult.failed("该招聘热度未增加！");
        return CommonResult.success(null, "热度增加!");
    }


    /**
     * 前端轮播图 头条数据获取
     */
    @ResponseBody
    @GetMapping("suthome/stuindex/bannertop")
    public CommonResult getTop() {
        List<Banner> bannerTopList = bannerService.getBannerTopList();
        if (bannerTopList.isEmpty()) return CommonResult.failed("未获取到头条数据！");
        return CommonResult.success(bannerTopList, "获取头条成功!");
    }

    /**
     * 个人中心
     */
    @ResponseBody
    @GetMapping("suthome/stuindex/myinfo")
    public CommonResult getInfo(@RequestParam(name = "uid") Integer uid) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).select("uid", "username", "realname", "age", "image_data", "create_time", "mobile", "email",  "pid", "identity_image","identification");
        Users list = stuUserService.getOne(wrapper);
        if (list == null) return CommonResult.failed("未获取到个人数据！");
        return CommonResult.success(list, "获取成功!");
    }

    /**
     * 通过ID修改学生头像
     *
     * @return
     */
    @ResponseBody
    @PostMapping("stu/editimg")
    public CommonResult stuEditImg(@RequestBody Users users) {
        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("uid", users.getUid());
        usersUpdateWrapper.set("image_data", users.getImageData());
        boolean update = stuUserService.update(users, usersUpdateWrapper);
        if (!update) return CommonResult.failed("修改失败！！");
        return CommonResult.success(null, "修改成功！！");
    }



    /**
     * 修改密码
     */
    @ResponseBody
    @PostMapping("stu/edit/password")
    public CommonResult stuEditPassword(@RequestBody EditPasswordVo editPasswordVo) {
        //判断传递来的是否一样
        if (Objects.equals(editPasswordVo.getOldPassword(), editPasswordVo.getPassword()))
            return CommonResult.failed("密码不能一致！！");
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        //通过uid得到原本的盐
        wrapper.eq("uid", editPasswordVo.getUid());
        String salt = stuUserService.getOne(wrapper).getSalt();

        //原本加密手段之后去对比
        String s = MD5Utils.md5(editPasswordVo.getOldPassword() + salt);
        wrapper.eq("password", s);
        Users one = stuUserService.getOne(wrapper);
        //如果对比错误就不一样
        if (one == null) return CommonResult.failed("旧密码错误！！");
        //对比成功就初始化盐
        salt = UUIDUtils.getUUID();

        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        //新的密码 加密
        String newPass = MD5Utils.md5(editPasswordVo.getPassword() + salt);
        usersUpdateWrapper.eq("uid", editPasswordVo.getUid());
        //存新盐
        usersUpdateWrapper.set("salt", salt);
        //新密码
        usersUpdateWrapper.set("password", newPass);
        boolean update = stuUserService.update(usersUpdateWrapper);
        if (!update) return CommonResult.failed("修改密码失败！！");
        return CommonResult.success(null, "修改密码成功！！");
    }


    //获取项目根目录路径
    //以及文件要存储目录
    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";
    /**
     * 上传简历
     */
    @ResponseBody
    @PostMapping("stu/notes/upload/{uid}")
    /*public CommonResult upload(@PathVariable Integer uid,@RequestBody MultipartFile file) throws IOException {
        //先判断这学生数据库有没有三个简历 最多3个简历
        Integer notesConstById = sResumeService.getNotesConstById(uid);
        if (notesConstById >= 3){
            return CommonResult.failed("最多只能存储三份简历，请删除更换！");
        }

        //获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        //去除目录和后缀后的文件名
        String baseName = FilenameUtils.getBaseName(originalFilename);
        // 获取文件的后缀
        String extension = FilenameUtils.getExtension(originalFilename);

        //加上自己文件名
        String fileRealPath = ROOT_PATH + File.separator + originalFilename;

        File saveFile = new File(fileRealPath);
        //判断 我存储的这个文件的父级目录存在不
        if (!saveFile.getParentFile().exists()) {
            //不存在就创建这个父级目录
            saveFile.getParentFile().mkdirs();
        }
        //如果传递来的文件名字重复 就要重命名这个文件 时间戳+~
        if (saveFile.exists()) {
            long l = System.currentTimeMillis();
            //再初始化 获取到到的源文件路径名称 fileRealPath
            fileRealPath = ROOT_PATH + File.separator + l + "_" + baseName + "." + extension;
            originalFilename = l + "_" + baseName + "." + extension;
            saveFile = new File(fileRealPath);
        }

        String url = "http://" + ip + ":" + port + "/api/v1/stu/notes/download/" + originalFilename;

        //最后存入数据库
        SResume sResume = new SResume();
        sResume.setCreateTime(null);
        sResume.setUid(uid);
        sResume.setFilename(originalFilename);
        sResume.setFilepath(url);
        sResume.setFiletype(extension);
        boolean save = sResumeService.save(sResume);

        if (!save) return  CommonResult.failed("存入简历失败！！");
        file.transferTo(saveFile);
        return CommonResult.success(url, "上传成功");
    }*/
    public CommonResult upload(@PathVariable Integer uid, @RequestParam("file") MultipartFile file) {
        try {
            logger.info("上传请求开始，用户ID: {}", uid);

            // 先判断这学生数据库有没有三个简历 最多3个简历
            Integer notesConstById = sResumeService.getNotesConstById(uid);
            if (notesConstById >= 3) {
                return CommonResult.failed("最多只能存储三份简历，请删除更换！");
            }

            // 获取文件的原始名称
            String originalFilename = file.getOriginalFilename();
            logger.info("接收到的文件名: {}", originalFilename);

            // 去除目录和后缀后的文件名
            String baseName = FilenameUtils.getBaseName(originalFilename);
            // 获取文件的后缀
            String extension = FilenameUtils.getExtension(originalFilename);

            // 生成文件的实际路径
            String fileRealPath = ROOT_PATH + File.separator + originalFilename;
            File saveFile = new File(fileRealPath);

            // 判断文件的父级目录是否存在，不存在则创建
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }

            // 如果传递来的文件名字重复，则重命名文件
            if (saveFile.exists()) {
                long timestamp = System.currentTimeMillis();
                fileRealPath = ROOT_PATH + File.separator + timestamp + "_" + baseName + "." + extension;
                originalFilename = timestamp + "_" + baseName + "." + extension;
                saveFile = new File(fileRealPath);
            }

            String url = "http://" + ip + ":" + port + "/api/v1/stu/notes/download/" + originalFilename;

            // 存入数据库
            SResume sResume = new SResume();
            sResume.setCreateTime(null);
            sResume.setUid(uid);
            sResume.setFilename(originalFilename);
            sResume.setFilepath(url);
            sResume.setFiletype(extension);
            boolean save = sResumeService.save(sResume);

            if (!save) {
                logger.error("存入简历数据库失败，用户ID: {}", uid);
                return CommonResult.failed("存入简历失败！！");
            }

            // 保存文件到服务器
            file.transferTo(saveFile);
            logger.info("文件上传成功，路径: {}", fileRealPath);

            return CommonResult.success(url, "上传成功");
        } catch (IOException e) {
            logger.error("文件上传异常，用户ID: {}", uid, e);
            return CommonResult.failed("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            logger.error("未知错误，用户ID: {}", uid, e);
            return CommonResult.failed("未知错误: " + e.getMessage());
        }
    }

    /**
     * 获取简历
     */
    @ResponseBody
    @GetMapping("stu/notes/{uid}")
    public CommonResult getNotes(@PathVariable Integer uid){
        QueryWrapper<SResume> sResumeQueryWrapper = new QueryWrapper<>();
        sResumeQueryWrapper.eq("uid",uid);
        List<SResume> list = sResumeService.list(sResumeQueryWrapper);
        if (list.isEmpty()) return CommonResult.failed("当前无简历，请您添加！！");
        return CommonResult.success(list,"获取简历成功！！");
    }


    /**
     * 下载简历
     */
    @ResponseBody
    @GetMapping("stu/notes/download/{fileName}")
    public CommonResult download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        String filePath = ROOT_PATH + File.separator + fileName;
        File file = new File(filePath);
        if (!file.exists()){
            return CommonResult.failed("没有这文件！！");
        }
        //读取这个文件
        byte[] bytes = FileUtil.readBytes(file);
        //写出去
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        return CommonResult.success(bytes,"获取成功，正在下载");
    }

    /**
     * 删除简历
     */
    @ResponseBody
    @PostMapping("stu/notes/del")
    public CommonResult delNotes(@RequestParam(name = "uid") Integer uid,@RequestParam(name = "fileName") String fileName){

        //先删除文件夹的文件 再删除数据库对应的文件
//        File file = new File(ROOT_PATH+File.separator+fileName);
//        System.out.println(file.exists());
//        if (file.exists()) file.delete();


        QueryWrapper<SResume> sResumeQueryWrapper = new QueryWrapper<>();
        //名字已经确保不重复
        sResumeQueryWrapper.eq("uid",uid).eq("filename",fileName);

        boolean remove = sResumeService.remove(sResumeQueryWrapper);
        if (!remove) return CommonResult.failed("删除简历失败！！");
        return CommonResult.success(null,"删除简历成功！！");
    }



    /**
     * 投递简历
     * 直接存储这学生的uid、简历t_id、某个招聘rec_id
     */
    @Autowired
    NotesManageService notesManageService;
    @Autowired
    NotesManageMapper notesManageMapper;

    @ResponseBody
    @PostMapping("stu/notes/send")
    public CommonResult sendNotes(@RequestBody NotesManage notesManage){
        //判断一个学生投了同一个招聘，不给投 返回每份简历只能投一次
        Integer size =  notesManageService.getMyNotesCount(notesManage.getUid(),notesManage.getRecId());
        System.out.println(size);
        if (size >= 1) return CommonResult.failed("您已投递简历，请勿重复投递！！");
        boolean save = notesManageService.save(notesManage);
        if (!save) return CommonResult.failed("投递简历失败！！");
        return CommonResult.success(null,"投递简历成功，待商家回复！！");
    }


    @Autowired
    CommentService commentService;
    @Autowired
    CommentReplyService commentReplyService;

    /**
     * 通过简历id展现出对应的评论
     */
    @ResponseBody
    @GetMapping("stu/rec/comment/{rid}")
    public CommonResult commentShowByRecId(@PathVariable Integer rid)  {
        QueryWrapper<CommentAndChildrenVo> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("rec_id",rid);

        List<CommentAndChildrenVo> commentByRid = commentService.getCommentByRid(rid);
        if (commentByRid.isEmpty()) return CommonResult.failed("当前简历无评论");
        for (CommentAndChildrenVo commentAndChildrenVo : commentByRid) {
            //获取某个父级评论的所有子评论 存入
            List<CommentReplyAndUserInfoVo> commentReplyByCid = commentService.getCommentReplyByCid(commentAndChildrenVo.getComId());
            commentAndChildrenVo.setChildren(commentReplyByCid);
        }
        //返回前端
        return CommonResult.success(commentByRid,"获取评论成功");
    }

    /**
     * 增加一级评论
     */
    @ResponseBody
    @PostMapping("stu/rec/add/comment")
    public CommonResult addComment(@RequestBody Comment comment)  {
        comment.setCreateTime(null);
        comment.setComId(null);
        boolean save = commentService.save(comment);
        if (!save)  return CommonResult.failed("添加评论失败");
        return CommonResult.success(null,"添加评论成功");
    }


    /**
     * 增加二级评论 前端把com_id存入answer_id 外键
     */
    @ResponseBody
    @PostMapping("stu/rec/add/reply")
    public CommonResult addCommentReply(@RequestBody CommentReply comment)  {
        System.out.println(comment);
        comment.setCreateTime(null);
        comment.setRepId(null);
        boolean save = commentReplyService.save(comment);
        if (!save)  return CommonResult.failed("添加评论失败");
        return CommonResult.success(null,"添加评论成功");
    }

    /**
     * 删除指定父级评论
     */
    @ResponseBody
    @GetMapping("stu/rec/del/comment/{cid}")
    public CommonResult delComment(@PathVariable Integer cid)  {
        //先删除 子评论 再删除父级评论
        QueryWrapper<CommentReply> commentReplyQueryWrapper = new QueryWrapper<>();
        commentReplyQueryWrapper.eq("answer_id",cid);
        commentReplyService.remove(commentReplyQueryWrapper);

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("com_id",cid);

        boolean remove = commentService.remove(commentQueryWrapper);
        if (!remove)  return CommonResult.failed("删除评论失败");

        return CommonResult.success(null,"删除评论成功");
    }


    /**
     * 删除指定子级评论
     */
    @ResponseBody
    @GetMapping("stu/rec/del/reply/{rid}")
    public CommonResult delCommentReply(@PathVariable Integer rid)  {
        //先删除 子评论 再删除父级评论
        QueryWrapper<CommentReply> commentReplyQueryWrapper = new QueryWrapper<>();
        commentReplyQueryWrapper.eq("rep_id",rid);
        boolean remove = commentReplyService.remove(commentReplyQueryWrapper);
        if (!remove)  return CommonResult.failed("删除评论失败");

        return CommonResult.success(null,"删除评论成功");
    }


    /**
     * 通过学生id获取自己的投递信息
     * 返回某个招聘下的投递 以及状态码、通知
     */
    @ResponseBody
    @GetMapping("stu/deliver/{uid}")
    public CommonResult getMyDeliver(@PathVariable Integer uid)  {
        QueryWrapper<NotesManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        //获取学生自己投递的简历
        List<NotesManage> list1 = notesManageService.list(queryWrapper);
        HashMap<Integer, ArrayList<SResumeAndNid>> map = new HashMap<>();
        List<RecruitmentAndSonNotesManageVo> recruitmentAndSonNotesManageVos = new ArrayList<>();

        //遍历所有这老板旗下的收到的简历
        for (NotesManage notesManage : list1) {
            //如果Map集合中recid不存在就创建recid 指向 这招聘信息的所有简历
            if(!map.containsKey(notesManage.getRecId())){
                //获取这次不重复招聘信息 的 学生简历
                SResumeAndNid sResume = sResumeService.getOneTid(notesManage.getTId());
                sResume.setNId(notesManage.getNId());
                sResume.setState(notesManage.getState());
                sResume.setNotesInfo(notesManage.getNotesInfo());
                ArrayList<SResumeAndNid> sResumeLists = new ArrayList<>();
                sResumeLists.add(sResume);
                //存放到map中
                map.put(notesManage.getRecId(),sResumeLists);
            }else {
                //如果map中已经存在这简历了就要获取这map的学生简历，然后追加新的学生简历，再重置这集合
                ArrayList<SResumeAndNid> sResumes = map.get(notesManage.getRecId());
                ArrayList<SResumeAndNid> newsResumes = new ArrayList<>();
                SResumeAndNid sResume = sResumeService.getOneTid(notesManage.getTId());
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
        return CommonResult.success(recruitmentAndSonNotesManageVos,"....");
    }



}
