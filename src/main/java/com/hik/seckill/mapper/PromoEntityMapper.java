package com.hik.seckill.mapper;

import com.hik.seckill.model.entity.PromoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface PromoEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoEntity record);

    int insertSelective(PromoEntity record);

    PromoEntity selectByPrimaryKey(Integer id);

    PromoEntity selectByItemId(@Param("id") Integer id, @Param("startTime") Date startTime);

    int updateByPrimaryKeySelective(PromoEntity record);

    int updateByPrimaryKey(PromoEntity record);
}