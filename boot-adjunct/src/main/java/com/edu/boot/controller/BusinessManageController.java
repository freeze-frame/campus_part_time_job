package com.edu.boot.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.edu.boot.common.Base64Utils;
import com.edu.boot.common.ReturnCode;
import com.edu.boot.common.api.CommonResult;
import com.edu.boot.entity.BusinessShop;
import com.edu.boot.entity.NotesManage;
import com.edu.boot.entity.Vo.BusinessShopAllInfoVo;
import com.edu.boot.entity.Vo.RecruitmentAndSonNotesManageVo;
import com.edu.boot.service.BusinessShopService;
import com.edu.boot.service.NotesManageService;
import com.edu.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BusinessManageController {
    String defaultImgUser = ReturnCode.DEFAULT_IMG_USER;


    @Autowired
    BusinessShopService shopService;
    @Autowired
    private UserService userService;
    /**
     * 通过商家id获取店铺的信息列表
     */
    @ResponseBody
    @GetMapping("managehome/shop/{id}")
    public CommonResult shopShow(@PathVariable Integer id) {
        List<BusinessShopAllInfoVo> list = shopService.getBusinessShopInfoById(id);

        if (list.size() == 0) {
            return CommonResult.failed("该商家没有店铺信息，请添加！");
        }

        return CommonResult.success(list, "获取店铺列表成功！");
    }

    /**
     * 添加商店信息
     */
    @ResponseBody
    @PostMapping("managehome/shop/add")
    public CommonResult shopAdd(@RequestBody BusinessShop businessShop) throws IOException {
        if (businessShop.getBImage() == null) {
            businessShop.setBImage(getDefaultImage());
        }
        if (businessShop.getBIdentityImage() == null) {
            businessShop.setBIdentityImage(getDefaultImage());
        }
        boolean save = shopService.save(businessShop);
        if (save) return CommonResult.success(null,"添加成功！");
        return CommonResult.failed("添加失败！");
    }

    private String getDefaultImage() throws IOException {
        ClassPathResource readFile = new ClassPathResource(defaultImgUser);
        File file = readFile.getFile();
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile cMultiFile = new MockMultipartFile(file.getName(), file.getName(), "image/jpeg", inputStream);
        return Base64Utils.convertToBase64(cMultiFile);
    }


    /**
     * 修改商店信息
     */
    @ResponseBody
    @PostMapping("managehome/shop/edit")
    public CommonResult shopEdit(@RequestBody BusinessShop businessShop) {
        if (businessShop == null) CommonResult.failed("添加失败！");
        System.out.println("BusinessShop: " + businessShop);
        UpdateWrapper<BusinessShop> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("b_id", businessShop.getBId());
        usersUpdateWrapper.set("b_name", businessShop.getBName());
        usersUpdateWrapper.set("b_image", businessShop.getBImage());
        usersUpdateWrapper.set("b_head", businessShop.getBHead());
        usersUpdateWrapper.set("b_address", businessShop.getBAddress());
        usersUpdateWrapper.set("b_identity_image",businessShop.getBIdentityImage());
        boolean update = shopService.update(businessShop, usersUpdateWrapper);
        if (!update) return CommonResult.failed("店铺信息更新失败！");
        return CommonResult.success(null,"店铺信息更新成功！");
    }


    /**
     * 删除商店
     */
    @ResponseBody
    @GetMapping("managehome/shop/delete/{id}")
    public CommonResult shopDelete(@PathVariable Integer id) {
        QueryWrapper<BusinessShop> businessShopQueryWrapper = new QueryWrapper<>();
        businessShopQueryWrapper.eq("b_id", id);
        boolean remove = shopService.remove(businessShopQueryWrapper);
        if (!remove) CommonResult.failed("删除店铺失败！");
        return CommonResult.success(null,"删除店铺成功！");
    }

    @Autowired
    NotesManageService notesManageService;

    /**
     * 商家点击简历查看需要改变简历状态码 通过nid
     */
    @ResponseBody
    @GetMapping("notes/change/state/{nid}")
    public CommonResult changeState(@PathVariable Integer nid) {
        UpdateWrapper<NotesManage> notesManageUpdateWrapper = new UpdateWrapper<>();
        notesManageUpdateWrapper.eq("n_id",nid);
        notesManageUpdateWrapper.setSql("`state` = 2");
        boolean update = notesManageService.update(notesManageUpdateWrapper);
        if (!update) return CommonResult.failed("更新简历状态失败！");
        return CommonResult.success(null,"更新简历状态成功！");
    }


    /**
     * 简历通过功能
     * 商家添加通知信息、更改状态码为已通过状态
     */
    @ResponseBody
    @PostMapping("managehome/notes/change")
    public CommonResult changeNotesInfoAndState(@RequestBody NotesManage notesManage) {
        System.out.println(notesManage);
        UpdateWrapper<NotesManage> notesManageUpdateWrapper = new UpdateWrapper<>();
        notesManageUpdateWrapper.eq("n_id",notesManage.getNId());
        notesManageUpdateWrapper.set("notes_info",notesManage.getNotesInfo());
        notesManageUpdateWrapper.set("state",notesManage.getState());
        boolean update = notesManageService.update(notesManageUpdateWrapper);
        System.out.println(update);
        if (!update) return CommonResult.failed("简历通知失败！");
        return CommonResult.success(null,"简历通知成功！");
    }



}
