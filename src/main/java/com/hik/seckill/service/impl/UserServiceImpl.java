package com.hik.seckill.service.impl;

import com.hik.seckill.error.CommonException;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.mapper.UserMapper;
import com.hik.seckill.model.dto.UserInfoDTO;
import com.hik.seckill.model.entity.UserEntity;
import com.hik.seckill.model.param.UserQueryParam;
import com.hik.seckill.service.IUserService;
import com.hik.seckill.validator.ValidationResult;
import com.hik.seckill.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * Created by wangJinChang on 2019/11/4 19:24
 * 用户服务相关接口实现类
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public Boolean add(UserInfoDTO userRegisteDTO) throws CommonException {
        if (userRegisteDTO == null) {
            throw new CommonException(EmBusinessError.USER_INFO_ERROR.getErrCode(), EmBusinessError.USER_INFO_ERROR.getErrMsg());
        }
        UserEntity userEntity = convertFromDTO(userRegisteDTO);
        setUserEntityInfo(userEntity);
        ValidationResult result = validator.validate(userEntity);
        if (result.isHasErrors()) {
            throw new CommonException(EmBusinessError.USER_INFO_ERROR.getErrCode(), result.getErrMsg());
        }
        Integer rows = userMapper.add(userEntity);
        return rows > 0;
    }


    @Override
    public Boolean remove(Integer uid) {
        Integer rows = userMapper.remove(uid);
        return rows > 0;
    }

    @Override
    public Boolean modify(UserInfoDTO userInfoDTO) throws CommonException {
        if (Objects.isNull(userInfoDTO)) {
            throw new CommonException(EmBusinessError.USER_NOT_EXIST.getErrCode(), EmBusinessError.USER_NOT_EXIST.getErrMsg());
        }
        UserEntity userEntity = convertFromDTO(userInfoDTO);
        userEntity.setUpdateTime(new Date());
        Integer rows = userMapper.modify(userEntity);
        return rows > 0;
    }

    @Override
    public UserInfoDTO query(UserQueryParam userParam) throws CommonException {
        UserEntity userEntity = userMapper.query(userParam);
        if (userEntity == null) {
            throw new CommonException(EmBusinessError.USER_NOT_EXIST.getErrCode(), EmBusinessError.USER_NOT_EXIST.getErrMsg());
        }
        UserInfoDTO userInfoDTO = convertFromEntity(userEntity);
        if (StringUtils.isNotBlank(userInfoDTO.getPhone())) {
            String phone = userInfoDTO.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            userInfoDTO.setPhone(phone);
        }
        return userInfoDTO;
    }

    /**
     * 设置用户信息  状态设置为1,创建时间设置为当前时间,更新时间设置为当前时间
     *
     * @param userEntity 用户信息
     */
    private void setUserEntityInfo(UserEntity userEntity) {
        if (userEntity.getGender() == null || userEntity.getGender() == 0) {
            userEntity.setGender(3);
        }
        userEntity.setStatus(1);
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
    }

    /**
     * UserInfoDTO转换为UserEntity
     *
     * @param userRegisteDTO 用户信息数据传递对象
     * @return UserEntity
     */
    private UserEntity convertFromDTO(UserInfoDTO userRegisteDTO) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userRegisteDTO, userEntity);
        return userEntity;
    }

    /**
     * UserEntity转换为UserInfoDTO
     *
     * @param userEntity 用户实体类信息
     * @return UserInfoDTO
     */
    private UserInfoDTO convertFromEntity(UserEntity userEntity) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userEntity, userInfoDTO);
        userInfoDTO.setPwd("******");
        return userInfoDTO;
    }
}
