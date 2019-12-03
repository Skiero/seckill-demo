package com.hik.seckill.mapper;

import com.hik.seckill.model.entity.CartEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CartEntity record);

    int insertSelective(CartEntity record);

    CartEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CartEntity record);

    int updateByPrimaryKey(CartEntity record);

    int remove(@Param("goodsId") Integer goodsId, @Param("userId") Integer userId);

    List<CartEntity> selectByUserId(@Param("userId") Integer userId);
}