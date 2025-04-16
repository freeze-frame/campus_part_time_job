package com.edu.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.boot.common.*;
import com.edu.boot.common.api.CommonResult;
import com.edu.boot.entity.Users;
import com.edu.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 *     SUCCESS(200, "操作成功"),
 *     FAILED(500, "操作失败"),
 *     VALIDATE_FAILED(404, "参数检验失败"),
 *     UNAUTHORIZED(401, "暂未登录或token已过期"),
 *     FORBIDDEN(403, "没有相关权限");
 */
@Controller
public class UsersController {
    String defaultImgUser = ReturnCode.DEFAULT_IMG_USER;

    /**
     * 登录功能
     */
    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public CommonResult login(@RequestBody Users user){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(user.getUsername() != null, "username", user.getUsername());
        Users users = userService.getOne(queryWrapper);
        if (users == null) return CommonResult.failed("账号密码有误！！");
        // 密码校验
        String s = (MD5Utils.md5(user.getPassword()+users.getSalt()));
        if (users.getPassword().equals(s)==false){
//            new OutputObject(ReturnCode.FAIL,"密码不正确",user);
            return CommonResult.failed("账号密码有误！！");
        }
        queryWrapper.in(user.getPassword() != null, "password", s);
        // 通过用户名从数据库中查询出该用户
        if (users == null){
//            OutputObject(ReturnCode.FAIL,"用户不存在",user);
            return CommonResult.failed("用户不存在！！");
        }
        String token = TokenUtil.sign(new Users(user.getUsername(),s));
        HashMap<String,Object> hs =new HashMap<>();
        hs.put("uid",users.getUid());
        hs.put("username",users.getUsername());
        hs.put("realname",users.getRealname());
        hs.put("mobile",users.getMobile());
        hs.put("email",users.getEmail());
        hs.put("age",users.getAge());
        hs.put("token",token);
        hs.put("identityImage",users.getIdentityImage());
        hs.put("imageData",users.getImageData());
        hs.put("pid",users.getPid());


//        OutputObject(String.valueOf(HttpStatus.OK.value()),"成功",hs);
        return CommonResult.success(hs,"登录成功！！");

    }


    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public CommonResult addUser(@RequestBody Users user) {
        try {
            // 查询用户名是否存在
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",user.getUsername());
            Users users = userService.getOne(queryWrapper);
            System.out.println(users);
            if (users!=null){
                return CommonResult.failed("该用户已存在");
            }
            // 设置盐
            String salt = UUIDUtils.getUUID();
            user.setSalt(salt);
            // 设置密码加密
            String s = MD5Utils.md5(user.getPassword()+salt);
            // 设置用户默认头像
            user.setPassword(s);
            ClassPathResource readFile = new ClassPathResource(defaultImgUser);
            File file = readFile.getFile();
            MultipartFile cMultiFile = new MockMultipartFile(file.getName(), file.getName(), null, new FileInputStream(file));
            String base64img = Base64Utils.convertToBase64(cMultiFile);
            user.setImageData(base64img);
            userService.save(user);
            return CommonResult.success("注册成功,等待管理员通过身份审核");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("注册失败");
        }
    }

}
