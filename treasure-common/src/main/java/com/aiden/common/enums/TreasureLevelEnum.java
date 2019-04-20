package com.aiden.common.enums;

public enum TreasureLevelEnum {

    LOW_LEVEL(0, "低级别宝藏"),
    HIGHT_LEVEL(1, "高级别宝藏");
    private final int level;
    private final String message;

    TreasureLevelEnum(int level, String message) {
        this.level = level;
        this.message = message;
    }

    public int getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
    public static TreasureLevelEnum valueOf(Integer level){
        if(level==null){
            return null;
        }
        TreasureLevelEnum[] levelEnums=TreasureLevelEnum.values();
        for (TreasureLevelEnum levelEnum : levelEnums) {
            if(levelEnum.level==level){
                return levelEnum;
            }
        }
        return null;
    }
}
