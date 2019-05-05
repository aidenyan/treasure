package com.aiden.controller;

/**
 * Created by Administrator on 2019/4/19/019.
 */

import com.aiden.base.ResultCode;
import com.aiden.base.ResultModel;
import com.aiden.common.utils.DateUtils;
import com.aiden.common.utils.MobileUtils;
import com.aiden.common.utils.PasswrodUtils;
import com.aiden.common.utils.StringUtils;
import com.aiden.dto.SendResultDto;
import com.aiden.dto.UserDetailDto;
import com.aiden.dto.UserDto;
import com.aiden.dto.UserResultDto;
import com.aiden.entity.User;
import com.aiden.entity.UserDetail;
import com.aiden.exception.ParamException;
import com.aiden.service.MobileService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller("/login")
@Api(value = "login", tags = "loginController", description = "登录信息")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private MobileService mobileService;

    @Autowired
    private UserDetailService userDetailService;

    public final static Long VERFIY_LONG=30*60*1000L;

    private Map<String, String> mobileMp = new HashMap<>();

    @PostMapping("/send_sms")
    @ResponseBody
    @ApiOperation("发送登录的短信信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "来源", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deviceId", value = "机器码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sysToken", value = "sysToken", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<SendResultDto> sendMobile(String mobile, String source, String deviceId, @RequestHeader(value = "sysToken") String sysToken) {
        try {
            if (!sysToken.equals(AUTHOR_KEY)) {
                return new ResultModel<>(ResultCode.AUTHOR);
            }
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(mobile), "手机号码不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(source), "来源不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(deviceId), "机器码不能未空");
            veriftyTrue(MobileUtils.isPhone(mobile), "手机号不正确");

            String code = StringUtils.randomNum(6);
            SendResultDto sendResultDto = new SendResultDto();
            User user = userService.findByMobile(mobile);
            UserDetail userDetail = null;
            if (user!=null&&!org.springframework.util.StringUtils.isEmpty(user.getPassword())) {
                return new ResultModel<>(ResultCode.LOGIN_FAIL_ALREADY_REG);
            }
            if(user!=null&&user.getSendNum()!=null&&user.getSendNum()>TOATL_SEND_NUM){
                return new ResultModel<>(ResultCode.LOGIN_FAIL_SEND_NUM);
            }
            if (user != null) {
                if (user.getSendCreated() != null && (user.getSendCreated() + 60 * 1000L) >= DateUtils.now().getTime()) {
                    return new ResultModel<>(ResultCode.LOGIN_FAIL_SMS_ERROR);
                }
                user.setSendCreated(DateUtils.now().getTime());
                user.setDeviceId(deviceId);
                user.setSource(source);
                user.setVerifyCode(PasswrodUtils.md5(code, key));
                user.setVerifySendDate(DateUtils.now());
            } else {
                user = new User();
                user.setSendCreated(DateUtils.now().getTime());
                user.setMobile(mobile);
                user.setDeviceId(deviceId);
                user.setSource(source);
                user.setVerifyCode(PasswrodUtils.md5(code, key));
                user.setVerifySendDate(DateUtils.now());
                user.setNickName(mobile);
                userDetail = new UserDetail();
                userDetail.setInvitationCode(userDetailService.findInvitationCode());
            }
            boolean sendResult = mobileService.sendMobile(mobile, code);
            sendResultDto.setSendResult(sendResult);
            if (!sendResult) {
                return new ResultModel<>(ResultCode.SUCCESS, sendResultDto);
            }
            userService.update(user, userDetail);
            if (org.springframework.util.StringUtils.isEmpty(user.getToken())) {
                sendResultDto.setReg(true);
            } else {
                sendResultDto.setReg(false);
            }
            mobileMp.put(mobile, code);
            return new ResultModel<>(ResultCode.SUCCESS, sendResultDto);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }

    @PostMapping("/reg")
    @ResponseBody
    @ApiOperation("注册信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "invitedCode", value = "来源", required = false, paramType = "query", dataType = "String"),
    })
    public ResultModel<String> reg(String mobile, String verifyCode, String password, String invitedCode) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(mobile), "手机号码不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(password), "密码不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(verifyCode), "验证码不能未空");
            User user = userService.findByMobile(mobile);
            if (user == null) {
                return new ResultModel<>(ResultCode.LOGIN_FAIL_SEND_CODE);
            }
            if(user.getVerifySendDate()==null||DateUtils.now().getTime()-user.getVerifySendDate().getTime()>VERFIY_LONG){
                return new ResultModel<>(ResultCode.LOGIN_FAIL_VERIFY_DATE_ERROR);
            }
            if (!PasswrodUtils.verify(verifyCode.toLowerCase(), key, user.getVerifyCode())) {
                return new ResultModel<>(ResultCode.LOGIN_FAIL_VERIFY_CODE_ERROR);
            }
            if (!org.springframework.util.StringUtils.isEmpty(user.getInvitedCode())) {
                return new ResultModel<>(ResultCode.LOGIN_FAIL_ALREADY_INVITED_ERROR);
            }
            User updateUser=new User();
            updateUser.setId(user.getId());
            updateUser.setPassword(  PasswrodUtils.md5(password,key));
            User invitedUser = null;
            if (org.springframework.util.StringUtils.isEmpty(user.getInvitedCode()) && !org.springframework.util.StringUtils.isEmpty(invitedCode)) {
                UserDetail invitedUserDetail = userDetailService.findByInvitedCode(invitedCode);
                invitedUser = new User();
                if (invitedUserDetail == null) {
                    return new ResultModel<>(ResultCode.LOGIN_FAIL_INVITED_ERROR);
                }
                User tempInvitedUser = userService.find(invitedUserDetail.getUserId());
                if (tempInvitedUser == null) {
                    return new ResultModel<>(ResultCode.LOGIN_FAIL_INVITED_ERROR);
                }
                if (tempInvitedUser.getId().equals(user.getId())) {
                    return new ResultModel<>(ResultCode.LOGIN_FAIL_INVITED_SELF_ERROR);
                }
                invitedUser.setId(tempInvitedUser.getId());
                invitedUser.setLuckPoin(tempInvitedUser.getLuckPoin() == null ? 1 : tempInvitedUser.getLuckPoin() + 1);
                invitedUser.setIntegral(tempInvitedUser.getIntegral() == null ? 1 : tempInvitedUser.getIntegral() + 1);
                invitedUser.setFindTreasurePoint(tempInvitedUser.getFindTreasurePoint() == null ? 1 : tempInvitedUser.getFindTreasurePoint() + 1);
                invitedUser.setTreasurePoint(tempInvitedUser.getTreasurePoint() == null ? 1 : tempInvitedUser.getTreasurePoint() + 1);
            }
            updateUser.setInvitedCode(invitedCode);
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
             updateUser.setToken(uuid);
            userService.updateInvition(updateUser, invitedUser);
            return new ResultModel<>(ResultCode.SUCCESS,uuid);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }

    }

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation("登录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "来源", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deviceId", value = "机器码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "invitedCode", value = "来源", required = false, paramType = "query", dataType = "String"),
    })
    public ResultModel<UserResultDto> login(String mobile, String source, String deviceId, String password) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(mobile), "手机号码不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(password), "密码不能未空");
            User user = userService.findByMobile(mobile);
            if (user == null) {
                return new ResultModel<>(ResultCode.LOGIN_FAIL_USER_NOT_EXIST);
            }
            if (!PasswrodUtils.verify(password.toLowerCase(), key, user.getPassword())) {
                return new ResultModel<>(ResultCode.LOGIN_FAIL_PASSWORD_ERROR);
            }
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setToken(uuid);
            if (!org.springframework.util.StringUtils.isEmpty(source)) {
                updateUser.setSource(source);
            }
            if (!org.springframework.util.StringUtils.isEmpty(deviceId)) {
                updateUser.setDeviceId(deviceId);
            }

            userService.update(updateUser);
            UserDto userDto = new UserDto();
            user= userService.findByMobile(mobile);
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
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }

    @GetMapping("/find")
    @ResponseBody
    @ApiOperation("手机号码密码查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, paramType = "query", dataType = "String")

    })
    public ResultModel<String> findCode(String mobile) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(mobile), "手机号码不能未空");
            return new ResultModel<>(ResultCode.SUCCESS, mobileMp.get(mobile));
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }
}
