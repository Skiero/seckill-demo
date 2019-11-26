package com.hik.seckill.mapper;

import com.hik.seckill.model.entity.UserEntity;
import com.hik.seckill.model.param.UserQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wangJinChang on 2019/11/5 20:36
 * 用户相关持久层
 */
@Mapper
public interface UserMapper {
    /**
     * 新增用户
     *
     * @param entity 用户实体类
     * @return 受影响行数
     */
    Integer add(UserEntity entity);

    /**
     * 根据ID删除用户
     *
     * @param uid 用户ID
     * @return 受影响行数
     */
    Integer remove(@Param("uid") Integer uid);

    /**
     * 修改用户信息
     *
     * @param entity 用户实体类
     * @return 受影响行数
     */
    Integer modify(UserEntity entity);

    /**
     * 查询用户信息
     *
     * @param userParam 模糊查询参数
     * @return 用户实体类
     */
    UserEntity query(UserQueryParam userParam);
}
