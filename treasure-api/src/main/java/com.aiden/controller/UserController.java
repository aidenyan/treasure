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
            @ApiImplicitParam(name = "nickName", value = "昵称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", dataType = "Byte"),
            @ApiImplicitParam(name = "birthDay", value = "生日", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userDesc", value = "描述", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<Void> updateUser(@ApiParam(value = "上传头像") MultipartFile multipartFile,
                                        String nickName, Byte sex, String birthDay, String userDesc, @RequestHeader(value = "token") String token) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token),"token不能未空");
        if(multipartFile==null&&StringUtils.isEmpty(nickName)&&sex==null&&StringUtils.isEmpty(birthDay)&&StringUtils.isEmpty(userDesc)){
            return new ResultModel<>(ResultCode.USER_INFO_PARAM_BLANK);
        }
        User user = userService.findToken(token);
        if (user == null) {
            throw new UnloginException();
        }
        String headerUrl = null;
        if(multipartFile!=null){
        try {
            String fileType = multipartFile.getOriginalFilename();
            fileType = fileType.substring(fileType.lastIndexOf("."));
            headerUrl = FileUtils.saveFile(FILE_PATH + "/" + user.getId() + "/", multipartFile.getInputStream(), fileType);
        } catch (IOException e) {
            throw new FileException("写入图片信息失败！");
        }}
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
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token),"token不能未空");
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(newPassword),"新密码不能未空");
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(password),"老密码不能未空");

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


    @GetMapping("/invite_friend")
    @ResponseBody
    @ApiOperation("获取邀请码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<String> inviteFriend( @RequestHeader(value = "token") String token) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token),"token不能未空");
        User user = userService.findToken(token);
        if (user == null) {
            throw new UnloginException();
        }
        UserDetail userDetail = userDetailService.findByUserId(user.getId());
        if (userDetail == null) {
            throw new ServiceException("系统信息错误");
        }
        return new ResultModel<>(ResultCode.SUCCESS, userDetail.getInvitationCode());
    }

    @GetMapping("/user_info")
    @ResponseBody
    @ApiOperation("用户的基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    })
    public ResultModel<UserResultDto> userInfo( @RequestHeader(value = "token") String token) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token),"token不能未空");
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
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token),"token不能未空");
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
        Page<CashInfo, Void> page = cashInfoService.page(user.getId(), currentPage, pageSize);
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
                cashInfoDto.setStatus(StatusEnum.valueOf(cashInfo.getStatus()));
                cashInfoDto.setType(BalanceTypeEnum.valueOf(cashInfo.getType()));
                cashInfoDto.setUserId(user.getId());
                cashInfoDtoList.add(cashInfoDto);
            });
        }
        Page<CashInfoDto, BigDecimal> resultPage = page.convert(cashInfoDtoList, userDetail.getBalanceAmount());
        return new ResultModel<>(ResultCode.SUCCESS, resultPage);
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
    public ResultModel<Page<UserTreasureDto, Void>> page(Boolean isReceive,Integer pageSize, Integer currentPage, @RequestHeader(value = "token") String token) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token),"token不能未空");
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
        Page<UserTreasure, Void> page = treasureService.pageUserTreasure(user.getId(),isReceive, currentPage, pageSize);
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
                if(userTreasureDto.getType()==null||userTreasureDto.getLevel()==null){
                    return;
                }
                userTreasureDtoList.add(userTreasureDto);
            });
        }
        Page<UserTreasureDto, Void> resultPage = page.convert(userTreasureDtoList, null);
        return new ResultModel<>(ResultCode.SUCCESS, resultPage);
    }
}
