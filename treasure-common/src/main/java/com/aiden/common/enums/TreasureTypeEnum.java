package com.aiden.common.enums;

public enum TreasureTypeEnum {

    AMOUNT(1, "红包"),
    WORDS(2, "文字"),
    VOICE(3, "语音"),
    VIDEO(4, "视频");
    private final int type;
    private final String message;

    TreasureTypeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public static TreasureTypeEnum valueOf(Integer type) {
        if(type==null){
            return null;
        }
        TreasureTypeEnum[] levelEnums = TreasureTypeEnum.values();
        for (TreasureTypeEnum levelEnum : levelEnums) {
            if (levelEnum.type == type) {
                return levelEnum;
            }
        }
        return null;
    }
}
