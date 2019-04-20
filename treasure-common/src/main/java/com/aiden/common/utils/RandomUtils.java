package com.aiden.common.utils;

import java.util.Random;

public class RandomUtils {
    /**
     * 在总数total中receive的比例
     * @param total
     * @param receive
     * @return 是否中将
     */
    public static boolean randomReceive(Integer total,Integer receive){
        Random random=new Random();
        int nextInt=random.nextInt(total);
        if(nextInt<0){
            nextInt=0-nextInt;
        }
        if(nextInt<receive){
            return true;
        }else{
            return false;
        }
    }
}
