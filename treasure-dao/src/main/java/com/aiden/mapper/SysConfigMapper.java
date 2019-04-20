package com.aiden.mapper;

import com.aiden.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface SysConfigMapper {
  SysConfig findOne();

  /**
   * 处理更新领取红包
   * @param id
   * @param lowNum
   * @return
   */
  int updateTreasureLow(@Param("id") Long id,@Param("lowNum") Integer lowNum);

  /**
   * 处理更新领取红包
   * @param id
   * @param heightNum
   * @return
   */
  int updateTreasureHeight(@Param("id") Long id,@Param("heightNum") Integer heightNum);


  int updateTreasureAmount(@Param("id") Long id,@Param("alreadyAmount") BigDecimal alreadyAmount,@Param("newAlreadyAmount") BigDecimal newAlreadyAmount);
}