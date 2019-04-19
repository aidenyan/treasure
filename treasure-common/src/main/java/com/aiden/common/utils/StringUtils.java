package com.aiden.common.utils;

import java.util.Random;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class StringUtils {

    public static final char[] BASE_CHAR = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    /**
     * 随机产生对应位数的随机码
     *
     * @param length
     * @return
     */
    public static String random(int length) {
        Random random = new Random();
        StringBuffer result=new StringBuffer();
        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt();
            if(randomInt<0){
                randomInt=0-randomInt;
            }
            result.append(BASE_CHAR[randomInt%BASE_CHAR.length]);
        }
        return result.toString().toLowerCase();
    }

   public static void main(String[]arg){
        System.out.println(random(32));
   }
}
