package com.aiden.common.enums;

public enum BalanceTypeEnum {

    RED_ENVELOPES  (1, "红包"),
    CASH_WITHDRAWAL(2, "体现");
    private final int type;
    private final String message;

    BalanceTypeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
    public static BalanceTypeEnum valueOf(Integer type){
        if(type==null){
            return null;
        }
        BalanceTypeEnum[] typeEnums= BalanceTypeEnum.values();
        for (BalanceTypeEnum typeEnum : typeEnums) {
            if(typeEnum.type==type){
                return typeEnum;
            }
        }
        return null;
    }
}
