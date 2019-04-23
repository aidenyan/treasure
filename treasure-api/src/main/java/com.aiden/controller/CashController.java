package com.aiden.controller;

import com.aiden.base.Page;
import com.aiden.base.ResultCode;
import com.aiden.base.ResultModel;
import com.aiden.common.enums.BalanceTypeEnum;
import com.aiden.common.enums.StatusEnum;
import com.aiden.dto.CashInfoDto;
import com.aiden.entity.CashInfo;
import com.aiden.entity.User;
import com.aiden.entity.UserDetail;
import com.aiden.exception.ParamException;
import com.aiden.exception.UnloginException;
import com.aiden.service.CashInfoService;
import com.aiden.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller("/cash")
@Api(value = "cash", tags = "CashController", description = "现金信息")
public class CashController extends BaseController {
    @Autowired
    private CashInfoService cashInfoService;

    @Autowired
    private UserService userService;

    @GetMapping("/sure_cash")
    @ResponseBody
    @ApiOperation("确认体现信息 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysToken", value = "sysToken", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "余额申请的体现金额的ID", required = true, paramType = "query", dataType = "Long")
    })
    public ResultModel<Void> sureCash(Long id, @RequestHeader(value = "sysToken") String sysToken) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(sysToken), "sysToken不能未空");
        if (!sysToken.equals(AUTHOR_KEY)) {
            return new ResultModel<>(ResultCode.AUTHOR);
        }
        cashInfoService.updateStatus(StatusEnum.COMPLETE, id);
        return new ResultModel<>(ResultCode.SUCCESS);
    }

    @GetMapping("/balance/page")
    @ResponseBody
    @ApiOperation("获取钱包信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysToken", value = "sysToken", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页数", paramType = "query", dataType = "Integer"),

    })
    public ResultModel<Page<CashInfoDto, Void>> pageBalance(Integer pageSize, Integer currentPage, @RequestHeader(value = "sysToken") String sysToken) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(sysToken), "sysToken不能未空");
        if (!sysToken.equals(AUTHOR_KEY)) {
            return new ResultModel<>(ResultCode.AUTHOR);
        }
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        Page<CashInfo, Void> page = cashInfoService.page(null, BalanceTypeEnum.CASH_WITHDRAWAL, currentPage, pageSize);
        List<CashInfoDto> cashInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(page.getResult())) {
            page.getResult().forEach(cashInfo -> {
                CashInfoDto cashInfoDto = new CashInfoDto();
                cashInfoDto.setAccountName(cashInfo.getAccountName());
                cashInfoDto.setAccountNum(cashInfo.getAccountNum());
                cashInfoDto.setCashWithdrawal(cashInfo.getCashWithdrawal().multiply(BigDecimal.valueOf(-1)));
                cashInfoDto.setCompleteTime(cashInfo.getCompleteTime());
                cashInfoDto.setCreatedTime(cashInfo.getCreatedTime());
                cashInfoDto.setId(cashInfo.getId());
                cashInfoDto.setStatus(StatusEnum.valueOf(cashInfo.getStatus()));
                cashInfoDto.setType(BalanceTypeEnum.valueOf(cashInfo.getType()));
                cashInfoDtoList.add(cashInfoDto);
            });
        }
        Page<CashInfoDto, Void> resultPage = page.convert(cashInfoDtoList, null);
        return new ResultModel<>(ResultCode.SUCCESS, resultPage);
    }

    @PostMapping("/cash_withdrawal")
    @ResponseBody
    @ApiOperation("提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountNum", value = "账号（长度不超过80）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "accountName", value = "账号名字（如果支付宝长度不超过80）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "accountRealName", value = "账号用户的名字，即支付/微信对应的真实姓名长度不超过80", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cashWithdrawal", value = "提现金额", paramType = "query", required = true, dataType = "BigDecimal")
    })
    public ResultModel<Void> updateAccount(
            String accountName, String accountNum, String accountRealName, BigDecimal cashWithdrawal, @RequestHeader(value = "token") String token) {
        try {
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(token), "token不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(accountNum), "accountNum不能未空");
            veriftyTrue(!org.springframework.util.StringUtils.isEmpty(accountRealName), "accountRealName不能未空");
            veriftyTrue(cashWithdrawal != null, "cashWithdrawal不能未空");
            User user = userService.findToken(token);
            if (user == null) {
                throw new UnloginException();
            }


            return new ResultModel<>(ResultCode.SUCCESS);
        } catch (ParamException e) {
            return new ResultModel<>("-6", e.getMessage());
        }
    }
}
