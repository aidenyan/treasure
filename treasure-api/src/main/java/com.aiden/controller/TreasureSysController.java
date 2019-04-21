package com.aiden.controller;

import com.aiden.common.utils.DateUtils;
import com.aiden.dto.TreasureInfoDto;
import com.aiden.base.ResultCode;
import com.aiden.base.ResultModel;
import com.aiden.entity.TreasureDistributionInfo;
import com.aiden.entity.TreasureInfo;
import com.aiden.service.SysConfigService;
import com.aiden.service.TreasureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller("/sys/treasure")
@Api(value = "treasure_sys", tags = "TreasureSysController", description = "宝藏系统信息设置")
public class TreasureSysController {
    public static final String AUTHOR_KEY = "5npkwbx2luq0c8gchdc9tasaf9v9wj6f";



    @Autowired
    private TreasureService treasureService;

    @Autowired
    private SysConfigService sysConfigService;
    @PostMapping("/treasure_info_list")
    @ResponseBody
    @ApiOperation("宝藏的类型信息列表")
    @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    public ResultModel<List<TreasureInfoDto>> listTreasureInfo(@RequestHeader(value = "token") String token) {
        if (!token.equals(AUTHOR_KEY)) {
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
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "treasureId", value = "宝藏收藏类型的ID", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "lat", value = "纬度", paramType = "query", required = true, dataType = "BigDecimal"),
            @ApiImplicitParam(name = "lng", value = "经度", paramType = "query", required = true, dataType = "BigDecimal")
    })

    public ResultModel<Void> setSiteInfo(
            Long treasureId, BigDecimal lat, BigDecimal lng, @RequestHeader(value = "token") String token) {
        if (!token.equals(AUTHOR_KEY)) {
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
        treasureService.save(treasureDistributionInfo);
        return new ResultModel<>(ResultCode.SUCCESS);
    }


    @PostMapping("/set_info")
    @ResponseBody
    @ApiOperation("宝藏类型信息设置")
    @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
    public ResultModel<Void> set(@RequestBody TreasureInfoDto treasureInfoDto, @RequestHeader(value = "token") String token) {
        if (!token.equals(AUTHOR_KEY)) {
            return new ResultModel<>(ResultCode.AUTHOR);
        }
        TreasureInfo treasureInfo = new TreasureInfo();
        BeanUtils.copyProperties(treasureInfoDto, treasureInfo);

        treasureService.saveTreasureInfo(treasureInfo);
        return new ResultModel<>(ResultCode.SUCCESS);
    }


}
