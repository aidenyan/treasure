package com.aiden.service.impl;

import com.aiden.common.utils.MobileUtils;
import com.aiden.entity.SysConfig;
import com.aiden.exception.ServiceException;
import com.aiden.service.MobileService;
import com.aiden.service.SysConfigService;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2019/4/19/019.
 */
@Service
@Transactional(readOnly = true)
public class MobileServiceImpl implements MobileService {
    @Autowired
    private SysConfigService sysConfigService;
    @Override
    public boolean sendMobile(String mobile, String content) {
        if (!MobileUtils.isPhone(mobile)) {
            return false;
        }
        SysConfig sysConfig=sysConfigService.findOne();
        if(StringUtils.isEmpty(sysConfig.getAccessKey())||
                StringUtils.isEmpty(sysConfig.getAccessSecret())){
            throw new ServiceException("系统数据异常");
        }
        if(sysConfig.getSmsEnabled()==null||!sysConfig.getSmsEnabled()){
            return true;
        }
        String accessKey=sysConfig.getAccessKey();
        String accessSecret=sysConfig.getAccessSecret();
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("SignName", "seeker");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("TemplateCode", "SMS_163851584");
        Map<String,String> smsContentMap=new HashMap<>();
        smsContentMap.put("code",content);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(smsContentMap));
        try {
            CommonResponse response = client.getCommonResponse(request);
          Map<String,Object>  resultMap=JSON.parseObject(response.getData(),Map.class);
          return "Ok".equalsIgnoreCase((String)resultMap.get("Code"));
        } catch (ServerException e) {
            return false;
        } catch (ClientException e) {
            return false;
        }
    }
}
