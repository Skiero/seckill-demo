package com.hik.seckill.mapper;

import com.hik.seckill.model.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderEntity record);

    int insertSelective(OrderEntity record);

    OrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderEntity record);

    int updateByPrimaryKey(OrderEntity record);

    List<OrderEntity> selectByOrderId(@Param("idList") List<Integer> idList, @Param("userId") Integer userId);

    List<OrderEntity> selectAllOrder(@Param("userId") Integer userId);
}