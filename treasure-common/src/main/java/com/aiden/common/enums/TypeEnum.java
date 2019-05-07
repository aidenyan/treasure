package com.aiden.common.enums;

public enum TypeEnum {

    SYS  (1, "系统"),
    INVITED(2, "邀请码"),
    REG(3, "注册"),
    ;
    private final int type;
    private final String message;

    TypeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
    public static TypeEnum valueOf(Integer type){
        if(type==null){
            return null;
        }
        TypeEnum[] typeEnums= TypeEnum.values();
        for (TypeEnum typeEnum : typeEnums) {
            if(typeEnum.type==type){
                return typeEnum;
            }
        }
        return null;
    }
}
