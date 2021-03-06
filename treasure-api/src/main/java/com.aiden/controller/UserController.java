package com.aiden.controller;

import com.aiden.base.Page;
import com.aiden.base.ResultCode;
import com.aiden.base.ResultModel;
import com.aiden.common.enums.BalanceTypeEnum;
import com.aiden.common.enums.StatusEnum;
import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.common.enums.TreasureTypeEnum;
import com.aiden.common.utils.DateUtils;
import com.aiden.common.utils.FileUtils;
import com.aiden.common.utils.PasswrodUtils;
import com.aiden.dto.*;
import com.aiden.entity.CashInfo;
import com.aiden.entity.User;
import com.aiden.entity.UserDetail;
import com.aiden.entity.UserTreasure;
import com.aiden.exception.FileException;
import com.aiden.exception.ParamException;
import com.aiden.exception.ServiceException;
import com.aiden.exception.UnloginException;
import com.aiden.service.CashInfoService;
import com.aiden.service.TreasureService;
import com.aiden.service.UserDetailService;
import com.aiden.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private TreasureService treasureService;

    @Autowired
    private CashInfoService cashInfoService;

    @Autowired
    private UserDetailService userDetailService;


    @PostMapping("/update_user")
    @ResponseBody
    @ApiOperation("更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称（长度不超过50）", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别,0:女，1:男生", paramType = "query", dataType = "Byte"),
            @ApiImplicitParam(name = "birthDay", value = "生日（yyyy-MM-dd）", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userDesc", value = "描述（长度不超过255）", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<Void> updateUser(@ApiParam(value = "上传头像") MultipartFile multipartFile,
                                        String nickName, Byte sex, String birthDay, String userDesc, @RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            veriftyTrue(sex == null || sex.equals(Byte.valueOf("0")) || sex.equals(Byte.valueOf("1")), "sex只能为0或者1");
            veriftyTrue(nickName == null || nickName.length() < 50, "长度不能超过50");
            veriftyTrue(birthDay == null || birthDay.length() < 20, "长度不能超过50");
            if (multipartFile == null && StringUtils.isEmpty(nickName) && sex == null && StringUtils.isEmpty(birthDay) && StringUtils.isEmpty(userDesc)) {
                return new ResultModel<>(ResultCode.USER_INFO_PARAM_BLANK);
            }
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }
            UserDetail userDetail = userDetailService.findByUserId(user.getId());
            UserDetail updateDetail = new UserDetail();
            updateDetail.setUserId(user.getId());
            boolean updateCouldDetail = false;
            String headerUrl = null;
            if (multipartFile != null) {
                try {
                    String fileType = multipartFile.getOriginalFilename();
                    fileType = fileType.substring(fileType.lastIndexOf("."));
                    headerUrl = FileUtils.saveFile(FILE_PATH + "/" + user.getId() + "/", multipartFile.getInputStream(), fileType);
                    updateCouldDetail = true;
                    updateDetail.setHeaderUrl(headerUrl);
                } catch (IOException e) {
                    throw new FileException("写入图片信息失败！");
                }
            }
            User updateUser = null;
            if (!StringUtils.isEmpty(nickName)) {
                updateUser = new User();
                updateUser.setId(user.getId());
                updateUser.setNickName(nickName);
            }

            if (userDetail != null) {
                updateDetail.setId(userDetail.getId());
                if (StringUtils.isEmpty(userDetail.getInvitationCode())) {
                    updateDetail.setInvitationCode(userDetailService.findInvitationCode());
                    updateCouldDetail = true;
                }
            } else {
                updateDetail.setUserId(user.getId());
                updateDetail.setInvitationCode(userDetailService.findInvitationCode());
                updateCouldDetail = true;
            }
            if (!StringUtils.isEmpty(birthDay)) {
                updateDetail.setBirthDay(DateUtils.convert(birthDay, "yyyy-MM-dd"));
                updateCouldDetail = true;
            }
            if (!StringUtils.isEmpty(sex)) {
                updateDetail.setSex(sex);
                updateCouldDetail = true;
            }
            if (!StringUtils.isEmpty(userDesc)) {
                updateDetail.setIntroduce(userDesc);
                updateCouldDetail = true;
            }
            if (!updateCouldDetail) {
                updateDetail = null;
            }
            userService.update(updateUser, updateDetail);
            return new ResultModel<>(ResultCode.SUCCESS);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
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
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(newPassword), "新密码不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(password), "老密码不能未空");

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
            userService.update(updateUser);
            return new ResultModel<>(ResultCode.SUCCESS);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }


    @GetMapping("/invite_friend")
    @ResponseBody
    @ApiOperation("获取邀请码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<String> inviteFriend(@RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }
            UserDetail userDetail = userDetailService.findByUserId(user.getId());
            if (userDetail == null) {
                throw new ServiceException("系统信息错误");
            }
            return new ResultModel<>(ResultCode.SUCCESS, userDetail.getInvitationCode());
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }

    @GetMapping("/user_info")
    @ResponseBody
    @ApiOperation("用户的基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<UserResultDto> userInfo(@RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }
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
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }

    @GetMapping("/balance/page")
    @ResponseBody
    @ApiOperation("获取钱包信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页数", paramType = "query", dataType = "Integer"),

    })
    public ResultModel<Page<CashInfoDto, BigDecimal>> pageBalance(Integer pageSize, Integer currentPage, @RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }
            if (currentPage == null) {
                currentPage = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            UserDetail userDetail = userDetailService.findByUserId(user.getId());
            if (userDetail == null) {
                throw new ServiceException("系统信息错误");
            }
            Page<CashInfo, Void> page = cashInfoService.page(user.getId(), null, currentPage, pageSize);
            List<CashInfoDto> cashInfoDtoList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(page.getResult())) {
                page.getResult().forEach(cashInfo -> {
                    CashInfoDto cashInfoDto = new CashInfoDto();
                    cashInfoDto.setAccountName(cashInfo.getAccountName());
                    cashInfoDto.setAccountNum(cashInfo.getAccountNum());
                    cashInfoDto.setCashWithdrawal(cashInfo.getCashWithdrawal());
                    cashInfoDto.setCompleteTime(cashInfo.getCompleteTime());
                    cashInfoDto.setCreatedTime(cashInfo.getCreatedTime());
                    cashInfoDto.setId(cashInfo.getId());
                    cashInfoDto.setUserId(cashInfo.getUserId());
                    cashInfoDto.setStatus(StatusEnum.valueOf(cashInfo.getStatus()));
                    cashInfoDto.setType(BalanceTypeEnum.valueOf(cashInfo.getType()));
                    cashInfoDto.setUserId(user.getId());
                    cashInfoDtoList.add(cashInfoDto);
                });
            }
            Page<CashInfoDto, BigDecimal> resultPage = page.convert(cashInfoDtoList, userDetail.getBalanceAmount());
            return new ResultModel<>(ResultCode.SUCCESS, resultPage);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }

    @GetMapping("/treasure/page")
    @ResponseBody
    @ApiOperation("获取用户宝藏信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页数", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isReceive", value = "是否中奖", paramType = "query", dataType = "Boolean"),
    })
    public ResultModel<Page<UserTreasureDto, Void>> page(Boolean isReceive, Integer pageSize, Integer currentPage, @RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            if (currentPage == null) {
                currentPage = 1;
            }
            Page<UserTreasure, Void> page = treasureService.pageUserTreasure(user.getId(), isReceive, currentPage, pageSize);
            List<UserTreasureDto> userTreasureDtoList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(page.getResult())) {
                page.getResult().forEach(userTreasure -> {
                    UserTreasureDto userTreasureDto = new UserTreasureDto();
                    userTreasureDto.setAddress(userTreasure.getAddress());
                    userTreasureDto.setAmount(userTreasure.getAmount());
                    userTreasureDto.setContent(userTreasure.getContent());
                    userTreasureDto.setIsReceive(userTreasure.getIsReceive());
                    userTreasureDto.setReceiveTime(userTreasure.getReceiveTime());
                    userTreasureDto.setTreasureName(userTreasure.getTreasureName());
                    userTreasureDto.setLevel(TreasureLevelEnum.valueOf(userTreasure.getLevel()));
                    userTreasureDto.setType(TreasureTypeEnum.valueOf(userTreasure.getType()));
                    if (userTreasureDto.getType() == null || userTreasureDto.getLevel() == null) {
                        return;
                    }
                    userTreasureDtoList.add(userTreasureDto);
                });
            }
            Page<UserTreasureDto, Void> resultPage = page.convert(userTreasureDtoList, null);
            return new ResultModel<>(ResultCode.SUCCESS, resultPage);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }


    @PostMapping("/update_account")
    @ResponseBody
    @ApiOperation("更新提现账号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountNum", value = "账号（长度不超过80）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "accountName", value = "账号名字（如果支付宝长度不超过80）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "accountRealName", value = "账号用户的名字，即支付/微信对应的真实姓名长度不超过80", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<Void> updateAccount(
            String accountName, String accountNum, String accountRealName, @RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(accountNum), "accountNum不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(accountRealName), "accountRealName不能未空");
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }
            String headerUrl = null;


            UserDetail userDetail = userDetailService.findByUserId(user.getId());

            if (userDetail == null) {
                throw new UnloginException();
            }
            UserDetail updateDetail = new UserDetail();
            updateDetail.setId(userDetail.getId());
            updateDetail.setAccountName(accountName);
            updateDetail.setAccountNum(accountNum);
            updateDetail.setAccountRealName(accountRealName);
            userService.update(updateDetail);
            return new ResultModel<>(ResultCode.SUCCESS);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }

    @PostMapping("/update_idcard")
    @ResponseBody
    @ApiOperation("实名认证信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", value = "省负责（长度不超过80）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "accountRealName", value = "账号用户的名字，即支付/微信对应的真实姓名长度不超过80", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<Void> updateIdCard(
            String idCard, String accountRealName, @RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(idCard), "idCard不能未空");
            veriftyTrue(idCard.length() < 80, "idCard长度不能超过80");

            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(accountRealName), "accountRealName不能未空");
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }
            UserDetail userDetail = userDetailService.findByUserId(user.getId());
            if (userDetail == null) {
                throw new UnloginException();
            }
            UserDetail updateDetail = new UserDetail();
            updateDetail.setId(userDetail.getId());
            updateDetail.setIdentificationCard(idCard);
            updateDetail.setHasCertification(true);
            updateDetail.setAccountRealName(accountRealName);
            userService.update(updateDetail);
            return new ResultModel<>(ResultCode.SUCCESS);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }


}
