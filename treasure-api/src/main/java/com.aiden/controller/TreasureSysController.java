package com.aiden.controller;

import com.aiden.base.ResultCode;
import com.aiden.base.ResultModel;
import com.aiden.common.enums.TreasureTypeEnum;
import com.aiden.common.utils.DateUtils;
import com.aiden.dto.TreasureInfoDto;
import com.aiden.entity.TreasureDistributionInfo;
import com.aiden.entity.TreasureInfo;
import com.aiden.service.TreasureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller("/sys/treasure")
@Api(value = "treasure_sys", tags = "TreasureSysController", description = "宝藏系统信息设置")
public class TreasureSysController extends BaseController {

    @Autowired
    private TreasureService treasureService;

    @GetMapping("/treasure_info_list")
    @ResponseBody
    @ApiOperation("宝藏的类型信息列表")
    @ApiImplicitParam(name = "sysToken", value = "sysToken", paramType = "header", required = true, dataType = "String")
    public ResultModel<List<TreasureInfoDto>> listTreasureInfo(@RequestHeader(value = "sysToken") String sysToken) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(sysToken), "sysToken不能未空");
        if (!sysToken.equals(AUTHOR_KEY)) {
            return new ResultModel<>(ResultCode.AUTHOR);
        }
        final List<TreasureInfo> treasureInfoList = treasureService.findAll();
        if (org.springframework.util.CollectionUtils.isEmpty(treasureInfoList)) {
            return new ResultModel<>(ResultCode.SUCCESS);
        }
        final List<TreasureInfoDto> resultList = new ArrayList<>();
        treasureInfoList.forEach(treasureInfo -> {
            TreasureInfoDto treasureInfoDto = new TreasureInfoDto();
            BeanUtils.copyProperties(treasureInfo, treasureInfoDto);
            resultList.add(treasureInfoDto);
        });
        return new ResultModel<>(ResultCode.SUCCESS, resultList);
    }

    @PostMapping("/set_site_info")
    @ResponseBody
    @ApiOperation("宝藏位置信息设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysToken", value = "sysToken", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "treasureId", value = "宝藏收藏类型的ID", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "lat", value = "纬度", paramType = "query", required = true, dataType = "BigDecimal"),
            @ApiImplicitParam(name = "lng", value = "经度", paramType = "query", required = true, dataType = "BigDecimal"),
            @ApiImplicitParam(name = "address", value = "地址详细信息", paramType = "query", required = true, dataType = "String"),
    })

    public ResultModel<Void> setSiteInfo(
            Long treasureId, BigDecimal lat, BigDecimal lng, String address, @RequestHeader(value = "sysToken") String sysToken) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(sysToken), "sysToken不能未空");
        veriftyTrue(treasureId != null, "宝藏收藏类型的ID不能未空");
        veriftyTrue(lat != null, "纬度不能未空");
        veriftyTrue(lng != null, "经度不能未空");
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(address), "地址详细信息不能未空");
        if (!sysToken.equals(AUTHOR_KEY)) {
            return new ResultModel<>(ResultCode.AUTHOR);
        }
        TreasureInfo treasureInfo = treasureService.findTreasureInfo(treasureId);
        if (treasureInfo == null) {
            return new ResultModel<>(ResultCode.TREASURE_FAIL_NOT_EXIST);
        }
        TreasureDistributionInfo treasureDistributionInfo = new TreasureDistributionInfo();
        treasureDistributionInfo.setAmount(treasureInfo.getAmount());
        treasureDistributionInfo.setContent(treasureInfo.getContent());
        treasureDistributionInfo.setCreatedTime(DateUtils.now());
        treasureDistributionInfo.setCreateId(0L);
        treasureDistributionInfo.setTreasureId(treasureId);
        treasureDistributionInfo.setCreateType(0);
        treasureDistributionInfo.setType(treasureInfo.getType());
        treasureDistributionInfo.setLat(lat);
        treasureDistributionInfo.setLng(lng);
        treasureDistributionInfo.setAddress(address);
        treasureService.save(treasureDistributionInfo);
        return new ResultModel<>(ResultCode.SUCCESS);
    }

    @PostMapping("/set_info")
    @ResponseBody
    @ApiOperation("宝藏类型信息设置")
    @ApiImplicitParam(name = "sysToken", value = "sysToken", paramType = "header", required = true, dataType = "String")
    public ResultModel<Void> set(@RequestBody TreasureInfoDto treasureInfoDto, @RequestHeader(value = "sysToken") String sysToken) {
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(sysToken), "sysToken不能未空");
        veriftyTrue(treasureInfoDto != null, "宝藏信息不能未空");
        veriftyTrue(!org.springframework.util.StringUtils.isEmpty(treasureInfoDto.getTreasureName()), "宝藏名称不能未空");
        veriftyTrue(treasureInfoDto.getLevel() != null && (treasureInfoDto.getLevel() == 0 || treasureInfoDto.getLevel() == 1), "宝藏等级不能未空，且只能未0或者1");
        veriftyTrue(treasureInfoDto.getLevel() != null && (treasureInfoDto.getLevel() == 0 || treasureInfoDto.getLevel() == 1), "宝藏等级不能未空，且只能未0或者1");
        veriftyTrue(treasureInfoDto.getDistance() != null, "距离不能未空");
        veriftyTrue(treasureInfoDto.getProbability() != null, "中奖的概率不能未空");
        veriftyTrue(treasureInfoDto.getType() != null, "type不能未空");
        veriftyTrue(TreasureTypeEnum.valueOf(treasureInfoDto.getType()) != null, "type只能未1，2，3，4");

        if (!sysToken.equals(AUTHOR_KEY)) {
            return new ResultModel<>(ResultCode.AUTHOR);
        }
        TreasureInfo treasureInfo = new TreasureInfo();
        BeanUtils.copyProperties(treasureInfoDto, treasureInfo);

        treasureService.saveTreasureInfo(treasureInfo);
        return new ResultModel<>(ResultCode.SUCCESS);
    }


}
