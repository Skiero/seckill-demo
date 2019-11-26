package com.hik.seckill.service;

import com.hik.seckill.error.CommonException;
import com.hik.seckill.model.dto.UserInfoDTO;
import com.hik.seckill.model.param.UserQueryParam;

/**
 * Created by wangJinChang on 2019/11/4 19:17
 * 用户服务相关接口
 */
public interface IUserService {
    /**
     * 新增用户
     *
     * @param userRegisteDTO 用户注册信息
     * @return 新增用户是否成功 true成功  false失败
     */
    Boolean add(UserInfoDTO userRegisteDTO) throws CommonException;

    /**
     * 根据ID删除用户
     *
     * @param uid 用户ID
     * @return 根据ID删除用户是否成功 true成功  false失败
     */
    Boolean remove(Integer uid);

    /**
     * 修改用户信息
     *
     * @param userRegisteDTO 用户注册信息
     * @return 修改用户信息是否成功 true成功  false失败
     */
    Boolean modify(UserInfoDTO userRegisteDTO) throws CommonException;

    /**
     * 查询用户信息
     *
     * @param userParam 模糊查询条件
     * @return 根据查询条件获取的用户信息
     */
    UserInfoDTO query(UserQueryParam userParam) throws CommonException;
}
