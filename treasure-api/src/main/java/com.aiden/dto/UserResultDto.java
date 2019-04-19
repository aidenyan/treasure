package com.aiden.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@ApiModel("用户的信息结果")
public class UserResultDto {
    @ApiModelProperty("用户的基本信息")
    private UserDto userDto;
    @ApiModelProperty("用户的详细信息")
    private UserDetailDto userDetailDto;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public UserDetailDto getUserDetailDto() {
        return userDetailDto;
    }

    public void setUserDetailDto(UserDetailDto userDetailDto) {
        this.userDetailDto = userDetailDto;
    }
}
