package com.aiden.controller;

import com.aiden.common.utils.DateUtils;
import com.aiden.common.utils.FileUtils;
import com.aiden.common.utils.PasswrodUtils;
import com.aiden.dto.base.ResultCode;
import com.aiden.dto.base.ResultModel;
import com.aiden.entity.User;
import com.aiden.entity.UserDetail;
import com.aiden.exception.FileException;
import com.aiden.exception.UnloginException;
import com.aiden.service.UserDetailService;
import com.aiden.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Controller("/user")
@Api(value = "login", description = "用户信息")
public class UserController extends BaseController {
    public static final String FILE_PATH = "/header/img";
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping("/update_user")
    @ResponseBody
    @ApiOperation("更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", dataType = "Byte"),
            @ApiImplicitParam(name = "birthDay", value = "生日", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userDesc", value = "描述", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<Void> updateUser(@ApiParam(value = "上传头像", required = true) MultipartFile multipartFile,
                                        String nickName, Byte sex, String birthDay, String userDesc, @RequestHeader(value = "token") String token) {
        User user = userService.findToken(token);
        if (user == null) {
            throw new UnloginException();
        }
        String headerUrl = null;
        try {
            String fileType = multipartFile.getOriginalFilename();
            fileType = fileType.substring(fileType.lastIndexOf("."));
            headerUrl = FileUtils.saveFile(FILE_PATH + "/" + user.getId() + "/", multipartFile.getInputStream(), fileType);
        } catch (IOException e) {
            throw new FileException("写入图片信息失败！");
        }
        User updateUser = null;
        if (!StringUtils.isEmpty(nickName)) {
            updateUser = new User();
            updateUser.setNickName(nickName);
        }
        UserDetail userDetail = userDetailService.findByUserId(user.getId());
        UserDetail updateDetail = new UserDetail();
        if (userDetail != null) {
            updateDetail.setId(userDetail.getId());
            if (StringUtils.isEmpty(userDetail.getInvitationCode())) {
                updateDetail.setInvitationCode(userDetailService.findInvitationCode());
            }
        } else {
            updateDetail.setUserId(user.getId());
            updateDetail.setInvitationCode(userDetailService.findInvitationCode());
        }
        if (!StringUtils.isEmpty(birthDay)) {
            updateDetail.setBirthDay(DateUtils.convert(birthDay, "yyyy-MM-dd"));
        }
        updateDetail.setSex(sex);
        if (!StringUtils.isEmpty(userDesc)) {
            updateDetail.setIntroduce(userDesc);
        }
        updateDetail.setHeaderUrl(headerUrl);
        userService.update(user, updateDetail);
        return new ResultModel<>(ResultCode.SUCCESS);
    }

    @PostMapping("/update_password")
    @ResponseBody
    @ApiOperation("更新密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<Void> updatePassword(String password, String newPassword, @RequestHeader(value = "token") String token) {
        User user = userService.findToken(token);
        if (user == null) {
            throw new UnloginException();
        }
        if (!PasswrodUtils.verify(password.toLowerCase(), key, user.getPassword())) {
            return new ResultModel<>(ResultCode.USER_FAIL_PASSWORD_ERROR);
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setPassword(PasswrodUtils.md5(newPassword.toLowerCase(), key));
        userService.update(user);
        return new ResultModel<>(ResultCode.SUCCESS);

    }

}
