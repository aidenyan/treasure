package com.aiden.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2019/4/11/011.
 */
@Controller("/swagger")
@Api(value = "swagger", tags = "SwaggerController",description ="样例" )//api中(tags和value)出现中文会导致api的接口无法点击开来，description可以用中文
public class SwaggerController {
    @ApiOperation(value = "demmo")
    @RequestMapping(value = "demo1", method = {RequestMethod.GET})
    @ResponseBody
    //@ApiImplicitParams
    //name对应的参数名字
    //    value：参数的汉字说明、解释
    //    required：参数是否必须传
    //    paramType：参数放在哪个地方
    //            · header --> 请求参数的获取：@RequestHeader
    //            · query --> 请求参数的获取：@RequestParam
    //            · path（用于restful接口）--> 请求参数的获取：@PathVariable
    //            · body（不常用）
    //            · form（不常用）
    //    dataType：参数类型，默认String，其它值dataType="Integer"
    //    defaultValue：参数的默认值
    @ApiImplicitParam(name = "id", value = "条码", paramType = "query", dataType = "String")
    public String demo(String id) {
        return "test" + id;
    }

    @ApiOperation(value = "demmo")
    @RequestMapping(value = "demo2", method = {RequestMethod.GET})
    @ResponseBody
    @ApiImplicitParam(name = "id", value = "条码", paramType = "query", dataType = "String")
    public String demo2(String id) {
        return "test" + id;
    }


}
