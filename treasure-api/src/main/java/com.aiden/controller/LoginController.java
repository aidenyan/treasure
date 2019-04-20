package com.aiden.controller;

/**
 * Created by Administrator on 2019/4/19/019.
 */

import com.aiden.common.utils.PasswrodUtils;
import com.aiden.common.utils.StringUtils;
import com.aiden.dto.UserDetailDto;
import com.aiden.dto.UserDto;
import com.aiden.dto.UserResultDto;
import com.aiden.dto.base.ResultCode;
import com.aiden.dto.base.ResultModel;
import com.aiden.entity.SysConfig;
import com.aiden.entity.User;
import com.aiden.entity.UserDetail;
import com.aiden.service.MobileService;
import com.aiden.service.SysConfigService;
import com.aiden.service.UserDetailService;
import com.aiden.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller("/login")
@Api(value = "login", tags = "loginController", description = "登录信息")
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private MobileService mobileService;
    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private UserDetailService userDetailService;

    private Map<String, String> mobileMp = new HashMap<>();

    @GetMapping("/send_sms")
    @ResponseBody
    @ApiOperation("发送登录的短信信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "来源", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deviceId", value = "机器码", required = true, paramType = "query", dataType = "String"),
    })
    public ResultModel<Void> sendMobile(String mobile, String source, String deviceId) {
        String code = StringUtils.random(6);
        mobileMp.put(mobile, code);
        SysConfig sysConfig = sysConfigService.findOne();
        if (sysConfig == null || org.springframework.util.StringUtils.isEmpty(sysConfig.getSmsContent())) {
            return new ResultModel<>(ResultCode.ERROR);
        }
        String smsContent = sysConfig.getSmsContent();
        smsContent = smsContent.replace("${code}", code);
        User user = userService.findByMobile(mobile);
        if (user != null) {
            user.setDeviceId(deviceId);
            user.setSource(source);
            user.setPassword(PasswrodUtils.md5(code, key));
            userService.update(user);
        } else {
            user = new User();
            user.setMobile(mobile);
            user.setDeviceId(deviceId);
            user.setSource(source);
            user.setPassword(PasswrodUtils.md5(code, key));
            user.setNickName(mobile);
            UserDetail userDetail=new UserDetail();
            userDetail.setInvitationCode(userDetailService.findInvitationCode());

            userService.update(user,userDetail);
        }
        mobileService.sendMobile(mobile, smsContent);
        return new ResultModel<>(ResultCode.SUCCESS);
    }

    @GetMapping("/login")
    @ResponseBody
    @ApiOperation("登录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "passwrod", value = "密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "来源", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deviceId", value = "机器码", required = false, paramType = "query", dataType = "String"),
    })
    public ResultModel<UserResultDto> login(String mobile, String source, String deviceId, String passwrod) {
        User user = userService.findByMobile(mobile);
        if (user == null) {
            return new ResultModel<>(ResultCode.LOGIN_FAIL_USER_NOT_EXIST);
        }
        if (!PasswrodUtils.verify(passwrod.toLowerCase(), key, user.getPassword())) {
            return new ResultModel<>(ResultCode.LOGIN_FAIL_PASSWORD_ERROR);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setToken(uuid);
        userService.update(updateUser);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        UserResultDto userResultDto = new UserResultDto();
        userResultDto.setUserDto(userDto);
        UserDetail userDetail = userDetailService.findByUserId(user.getId());
        if (userDetail != null) {
            UserDetailDto userDetailDto = new UserDetailDto();
            BeanUtils.copyProperties(userDetail, userDetailDto);
            userResultDto.setUserDetailDto(userDetailDto);
        }
        return new ResultModel<>(ResultCode.SUCCESS, userResultDto);
    }

    @GetMapping("/find")
    @ResponseBody
    @ApiOperation("手机号码密码查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query", dataType = "String")

    })
    public ResultModel<String> findCode(String mobile) {
        return new ResultModel<>(ResultCode.SUCCESS, mobileMp.get(mobile));
    }
}