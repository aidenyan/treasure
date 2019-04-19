package com.aiden.controller;

import com.aiden.dto.TreasureInfoDto;
import com.aiden.dto.base.ResultCode;
import com.aiden.dto.base.ResultModel;
import com.aiden.entity.TreasureInfo;
import com.aiden.service.TreasureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Controller("/treasure")
@Api(value = "treasure", tags = "TreasureController", description = "挖宝信息")
public class TreasureController {
    public static final String AUTHOR_KEY = "5npkwbx2luq0c8gchdc9tasaf9v9wj6f";

    @Autowired
    private TreasureService treasureService;

    @PostMapping("/set_info")
    @ResponseBody
    @ApiOperation("宝藏信息设置")
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
