package com.aiden.controller;

import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.common.enums.TreasureTypeEnum;
import com.aiden.common.utils.DateUtils;
import com.aiden.common.utils.RandomUtils;
import com.aiden.dto.TreasureOpenResultDto;
import com.aiden.dto.UnreceiveTreasureDto;
import com.aiden.base.ResultCode;
import com.aiden.base.ResultModel;
import com.aiden.entity.*;
import com.aiden.exception.UnloginException;
import com.aiden.service.SysConfigService;
import com.aiden.service.TreasureService;
import com.aiden.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Controller("/treasure")
@Api(value = "treasure", tags = "TreasureController", description = "挖宝信息")
public class TreasureController {

    @Autowired
    private UserService userService;

    @Autowired
    private TreasureService treasureService;

    @Autowired
    private SysConfigService sysConfigService;


    @PostMapping("/treasure_unreceive_list")
    @ResponseBody
    @ApiOperation("获取用户附近宝藏 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "distance", value = "搜索范围的距离", paramType = "query",  dataType = "BigDecimal"),
            @ApiImplicitParam(name = "lat", value = "纬度", paramType = "query", required = true, dataType = "BigDecimal"),
            @ApiImplicitParam(name = "lng", value = "经度", paramType = "query", required = true, dataType = "BigDecimal")
    })
    public ResultModel<List<UnreceiveTreasureDto>> listUnreceiveTreasure(BigDecimal lat, BigDecimal lng, BigDecimal distance,
                                                                         @RequestHeader(value = "token") String token) {
        User user = userService.findToken(token);
        if (user == null) {
            throw new UnloginException();
        }
        /**
         * 系统处理
         */
        SysConfig sysConfig = sysConfigService.findOne();
        if (sysConfig == null) {
            return new ResultModel<>(ResultCode.ERROR);
        }
        if (sysConfig.getTreasureEnabled() == null || !sysConfig.getTreasureEnabled()) {
            return new ResultModel<>(ResultCode.TREASURE_FAIL_NOT_ENBALED);
        }
        if (sysConfig.getTreasureEndTime() != null && sysConfig.getTreasureEndTime().compareTo(DateUtils.now()) < 0) {
            return new ResultModel<>(ResultCode.TREASURE_FAIL_ALREADY_END);
        }
        final List<UnreceiveTreasure> unreceiveTreasureList = treasureService.findUnReceiveTreasure(lat,lng,distance);
        if (org.springframework.util.CollectionUtils.isEmpty(unreceiveTreasureList)) {
            return new ResultModel<>(ResultCode.SUCCESS);
        }
        final List<UnreceiveTreasureDto> resultList = new ArrayList<>();
        unreceiveTreasureList.forEach(unreceiveTreasure -> {
            UnreceiveTreasureDto unreceiveTreasureDto = new UnreceiveTreasureDto();
            unreceiveTreasureDto.setDistance(unreceiveTreasure.getDistance());
            unreceiveTreasureDto.setLat(unreceiveTreasure.getLat());
            unreceiveTreasureDto.setLevel(TreasureLevelEnum.valueOf(unreceiveTreasure.getLevel()));
            if(unreceiveTreasureDto.getLevel()==null){
                return;
            }
            unreceiveTreasureDto.setTreasureDistributionId(unreceiveTreasure.getTreasureDistributionId());
            unreceiveTreasureDto.setTreasureId(unreceiveTreasure.getTreasureId());
            unreceiveTreasureDto.setLng(unreceiveTreasure.getLng());
            unreceiveTreasureDto.setTreasureName(unreceiveTreasure.getTreasureName());
            unreceiveTreasureDto.setType(TreasureTypeEnum.valueOf(unreceiveTreasure.getType()));
            if(unreceiveTreasureDto.getType()==null){
                return;
            }
            resultList.add(unreceiveTreasureDto);
        });
        return new ResultModel<>(ResultCode.SUCCESS, resultList);
    }



    @PostMapping("/open_treasure")
    @ResponseBody
    @ApiOperation("打开宝藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "treasureDistributionId", value = "宝藏收藏的ID", paramType = "query", required = true, dataType = "String")
    })
    public ResultModel<TreasureOpenResultDto> openTreasure(Long treasureDistributionId, @RequestHeader(value = "token") String token) {
        User user = userService.findToken(token);
        if (user == null) {
            throw new UnloginException();
        }
        TreasureDistributionInfo treasureDistributionInfo = treasureService.findTreasureDistributionInfo(treasureDistributionId);
        if (treasureDistributionInfo == null) {
            return new ResultModel<>(ResultCode.ERROR);
        }
        if (treasureDistributionInfo.getTreasureId() == null) {
            return new ResultModel<>(ResultCode.ERROR);
        }
        TreasureInfo treasureInfo = treasureService.findTreasureInfo(treasureDistributionInfo.getTreasureId());
        if (treasureInfo == null) {
            return new ResultModel<>(ResultCode.ERROR);
        }
        if (treasureInfo.getEndTime() != null && treasureInfo.getEndTime().compareTo(DateUtils.now()) <= 0) {
            return new ResultModel<>(ResultCode.TREASURE_FAIL_ALREADY_END);
        }
        TreasureTypeEnum treasureTypeEnum = TreasureTypeEnum.valueOf(treasureInfo.getType());
        if (treasureTypeEnum == null) {
            return new ResultModel<>(ResultCode.ERROR);
        }

        /**
         * 系统处理
         */
        SysConfig sysConfig = sysConfigService.findOne();
        if (sysConfig == null) {
            return new ResultModel<>(ResultCode.ERROR);
        }
        if (sysConfig.getTreasureEnabled() == null || !sysConfig.getTreasureEnabled()) {
            return new ResultModel<>(ResultCode.TREASURE_FAIL_NOT_ENBALED);
        }
        if (sysConfig.getTreasureEndTime() != null && sysConfig.getTreasureEndTime().compareTo(DateUtils.now()) < 0) {
            return new ResultModel<>(ResultCode.TREASURE_FAIL_ALREADY_END);
        }
        if (treasureDistributionInfo.getUserId() != null) {
            return new ResultModel<>(ResultCode.TREASURE_FAIL_ALREADY_RECIEVE);
        }
        Integer alreadyNum = null;
        TreasureLevelEnum treasureLevelEnum = null;
        TreasureOpenResultDto resultDto = new TreasureOpenResultDto();
        if (treasureTypeEnum == TreasureTypeEnum.AMOUNT) {
            /**
             * 系统级别的比较金额是否已经使用完了
             * 比较活动是否已经收取完成
             */
            if (treasureInfo.getLevel() == null || TreasureLevelEnum.valueOf(treasureInfo.getLevel()) == null) {
                treasureLevelEnum = TreasureLevelEnum.LOW_LEVEL;
                alreadyNum = sysConfig.getTreasureAlreadyLowNum();
            } else {
                treasureLevelEnum = TreasureLevelEnum.valueOf(treasureInfo.getLevel());
                alreadyNum = sysConfig.getTreasureAlreadyHightNum();
            }

            boolean isOver = false;

            /**
             * 次数的控制
             */
            if (treasureLevelEnum == TreasureLevelEnum.LOW_LEVEL) {
                if (sysConfig.getTreasureLowNum() != null && sysConfig.getTreasureAlreadyLowNum() != null && sysConfig.getTreasureLowNum() <= sysConfig.getTreasureAlreadyLowNum()) {
                    isOver = true;
                }
            } else {
                if (sysConfig.getTreasureHightNum() != null && sysConfig.getTreasureAlreadyHightNum() != null && sysConfig.getTreasureHightNum() <= sysConfig.getTreasureAlreadyHightNum()) {
                    isOver = true;
                }
            }

            /**
             * 金额的控制
             */
            if (!isOver && sysConfig.getTreasureTotalAmount() != null) {
                BigDecimal treasureAlreadyTotalAmount = BigDecimal.ZERO;
                if (sysConfig.getTreasureAlreadyTotalAmount() != null) {
                    treasureAlreadyTotalAmount = treasureAlreadyTotalAmount.add(sysConfig.getTreasureAlreadyTotalAmount());
                }
                treasureAlreadyTotalAmount = treasureAlreadyTotalAmount.add(treasureInfo.getAmount());
                if (treasureAlreadyTotalAmount.compareTo(sysConfig.getTreasureTotalAmount()) > 0) {
                    isOver = true;
                }
            }
            /**
             * 本身活动的金额限制
             */
            if (!isOver && treasureInfo.getTotalAmount() != null) {
                BigDecimal treasureAlreadyTotalAmount = BigDecimal.ZERO;
                if (treasureInfo.getAlreadyAmount() != null) {
                    treasureAlreadyTotalAmount = treasureAlreadyTotalAmount.add(treasureInfo.getAlreadyAmount());
                }
                treasureAlreadyTotalAmount = treasureAlreadyTotalAmount.add(treasureInfo.getAmount());
                if (treasureAlreadyTotalAmount.compareTo(treasureInfo.getTotalAmount()) > 0) {
                    isOver = true;
                }
            }
            if (isOver) {
                resultDto.setResult(false);
                treasureService.openFailTreasure(treasureLevelEnum, treasureDistributionId, user.getId());
                return new ResultModel<>(ResultCode.SUCCESS, resultDto);
            }
        }
        BigDecimal probability = treasureInfo.getProbability();
        probability = probability.multiply(BigDecimal.valueOf(10000));
        boolean isRecieve = RandomUtils.randomReceive(1000000, probability.intValue());
        treasureService.openTreasure(treasureLevelEnum, treasureDistributionId, user.getId(), isRecieve, sysConfig.getId(), sysConfig.getTreasureAlreadyTotalAmount(),
                treasureInfo.getAlreadyAmount(), treasureInfo.getAmount(), alreadyNum);
        resultDto.setResult(isRecieve);
        resultDto.setAmount(treasureInfo.getAmount());
        resultDto.setContent(treasureInfo.getContent());
        resultDto.setLevel(treasureLevelEnum);
        resultDto.setTreasureName(treasureInfo.getTreasureName());
        resultDto.setType(treasureTypeEnum);
        return new ResultModel<>(ResultCode.SUCCESS, resultDto);
    }


}
