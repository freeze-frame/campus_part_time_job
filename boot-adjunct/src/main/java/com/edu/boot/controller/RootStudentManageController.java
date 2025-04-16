package com.edu.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.boot.common.api.CommonResult;
import com.edu.boot.entity.SResume;
import com.edu.boot.entity.Users;
import com.edu.boot.entity.Vo.SResumeAndUserVo;
import com.edu.boot.service.SResumeService;
import com.edu.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RootStudentManageController {
    @Autowired
    UserService stuManageService;
    @Autowired
    SResumeService sResumeService;
    /**
     * 学生管理列表
     * @return
     */
    @ResponseBody
    @GetMapping("stu/show")
    public CommonResult stuShow(@RequestParam(name = "currentpage") Integer current,@RequestParam(name = "pagesize") Integer size){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uid","username","realname","image_data","identity_image","create_time","mobile","email","age","pid","identification").eq("pid", 2);
        IPage<Users> page = stuManageService.page(new Page<>(current, size), queryWrapper);
        if (page.getRecords().isEmpty()) return CommonResult.failed("获取学生列表失败！！");
        return CommonResult.success(page,"获取学生列表成功！！");
    }

    /**
     * 通过ID修改学生信息
     * @return
     */
    @ResponseBody
    @PostMapping("stu/edit")
    public CommonResult stuEditById(@RequestBody Users users){
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
        boolean update = stuManageService.update(users, usersUpdateWrapper);
        if (!update) return CommonResult.failed("修改失败！！");
        return CommonResult.success(null,"修改成功！！");
    }

    /**
     * 通过ID删除学生信息
     * @return
     */
    @ResponseBody
    @PostMapping("stu/delete")
    public CommonResult stuDeleteById(@RequestBody Users users){
        QueryWrapper<SResume> sResumeQueryWrapper = new QueryWrapper<>();
        sResumeQueryWrapper.eq("uid",users.getUid());
        // 先删除简历
        sResumeService.remove(sResumeQueryWrapper);

        boolean b = stuManageService.removeById(users.getUid());
        if (!b)  return CommonResult.failed("删除失败！！");
        return CommonResult.success(null,"删除成功！！");
    }

    /*通过ID认证身份*/
    @ResponseBody
    @GetMapping("stu/stuIdentity")
    public CommonResult identityPassById(@RequestParam(name = "currentpage") Integer current,@RequestParam(name = "pagesize") Integer size){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uid","username","identity_image","pid","identification").eq("pid", 2);
        IPage<Users> page = stuManageService.page(new Page<>(current, size), queryWrapper);
        if (page.getRecords().isEmpty()) return CommonResult.failed("获取学生列表失败！！");
        return CommonResult.success(page,"获取学生列表成功！！");
    }

    /**
     * 通过用户名搜索数据
     * @return
     */
    @ResponseBody
    @GetMapping("stu/search")
    public CommonResult stuSearchByName(@RequestParam(name = "currentpage") Integer current,@RequestParam(name = "pagesize") Integer size,@RequestParam(name = "search") String search){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        //搜索返回指定字段 然后pid要是学生的
        wrapper.select("uid", "username","realname","identity_image", "image_data", "create_time", "mobile", "email", "age","pid","identification").like("realname", search).eq("pid", 2);
        IPage<Users> list = stuManageService.page(new Page<>(current,size),wrapper);
        if (list.getRecords().isEmpty()) return CommonResult.failed("搜索失败！！");
        return CommonResult.success(list,"搜索成功！！");
    }

    /**
     * 管理学生简历
     * @return
     */
    @ResponseBody
    @GetMapping("stu/notes/manage")
    public CommonResult sResumeManage(){
        List<SResumeAndUserVo> stuNotesAndUserInfo = sResumeService.getStuNotesAndUserInfo();
        if (stuNotesAndUserInfo.isEmpty()) return  CommonResult.failed("简历获取失败！！");
        return CommonResult.success(stuNotesAndUserInfo,"简历获取成功！！");
    }

}
