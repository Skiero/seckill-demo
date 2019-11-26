package com.hik.seckill.utils;

import com.alibaba.fastjson.JSON;
import com.hik.seckill.constant.CommonConstant;
import com.hik.seckill.constant.RedisConstant;
import com.hik.seckill.model.vo.UserInfoVO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by wangJinChang on 2019/11/23 15:42
 * CAS登录工具类
 */
public class CasUtil {
    /**
     * 获取登录用户的用户信息
     *
     * @param request HttpServletRequest
     * @return 登录的用户信息
     */
    private static UserInfoVO getUserInfo(HttpServletRequest request) {
        request = request == null ? HttpUtil.getRequest() : null;
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (Objects.isNull(cookies)) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if (Objects.equals(CommonConstant.COOKIE_USER_LOGIN_TOKEN, cookie.getName())) {
                    String redisKey = String.format(RedisConstant.USER_LOGIN_TOKEN + "%S", cookie.getValue());
                    String s = RedisUtil.get(redisKey);
                    return JSON.parseObject(s, UserInfoVO.class);
                }
            }
        }
        return null;
    }

    /**
     * 获取登录用户的用户名
     *
     * @return 登录用户的用户名
     */
    public static Optional<String> getUserName() {
        UserInfoVO userInfo = getUserInfo(null);
        if (Objects.isNull(userInfo)) {
            return Optional.empty();
        }
        return Optional.ofNullable(userInfo.getAccount());
    }

    /**
     * 获取登录用户的ID
     *
     * @return 登录用户的ID
     */
    public static Optional<Integer> getUserId() {
        UserInfoVO userInfo = getUserInfo(null);
        if (Objects.isNull(userInfo)) {
            return Optional.empty();
        }
        return Optional.ofNullable(userInfo.getUid());
    }

    /**
     * 多线程中获取登录用户的ID
     *
     * @param request HttpServletRequest
     * @return 登录用户的ID
     */
    public static Optional<Integer> getUserId(HttpServletRequest request) {
        UserInfoVO userInfo = getUserInfo(request);
        if (Objects.isNull(userInfo)) {
            return Optional.empty();
        }
        return Optional.ofNullable(userInfo.getUid());
    }

    /**
     * 获取登录用户的token(cookie的value)
     *
     * @return token
     */
    public static Optional<String> getUserToken() {
        HttpServletRequest request = HttpUtil.getRequest();
        if (request == null) {
            return Optional.empty();
        }
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return Optional.empty();
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(CommonConstant.COOKIE_USER_LOGIN_TOKEN, cookie.getName())) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }
}
