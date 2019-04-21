package com.aiden.common.enums;

public enum StatusEnum {

    INIT  (1, "初始化执行长"),
    COMPLETE(2, "完成");
    private final int status;
    private final String message;

    StatusEnum(int status, String message) {
        this.status =status;
        this.message = message;
    }


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    public static StatusEnum valueOf(Integer status){
        if(status==null){
            return null;
        }
        StatusEnum[] statusEnums= StatusEnum.values();
        for (StatusEnum statusEnum : statusEnums) {
            if(statusEnum.status==status){
                return statusEnum;
            }
        }
        return null;
    }
}
