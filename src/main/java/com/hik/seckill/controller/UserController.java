package com.hik.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.hik.seckill.constant.CommonConstant;
import com.hik.seckill.constant.RedisConstant;
import com.hik.seckill.error.CommonException;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.model.dto.UserInfoDTO;
import com.hik.seckill.model.param.UserInfoParam;
import com.hik.seckill.model.param.UserLoginParam;
import com.hik.seckill.model.param.UserQueryParam;
import com.hik.seckill.model.vo.ResultVO;
import com.hik.seckill.model.vo.UserInfoVO;
import com.hik.seckill.service.IUserService;
import com.hik.seckill.utils.RandomUtil;
import com.hik.seckill.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Created by wangJinChang on 2019/11/4 19:16
 * 用户控制器层
 */
@Api(tags = "用户控制器层")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/otp")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public ResultVO getOpt(@Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$", message = "输入的手机格式不正确") @RequestParam(value = "telephone") String phone) {
        //判断号码是否被注册
        UserQueryParam userQueryParam = new UserQueryParam();
        userQueryParam.setPhone(phone);
        try {
            UserInfoDTO query = userService.query(userQueryParam);
            if (!Objects.isNull(query)) {
                return ResultVO.error(EmBusinessError.USER_IS_EXIST.getErrCode(), "该手机号码已注册");
            }
        } catch (CommonException e) {
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        int otp = RandomUtil.randomLong(6);
        RedisUtil.set(String.format(RedisConstant.USER_LOGIN_TOKEN + "%S", phone), JSON.toJSONString(otp), 60 * 3);
        System.err.println("phone ==> " + phone + " , otp ==> " + otp);
        return ResultVO.success("获取成功", otp);
    }

    @PostMapping("/registe")
    @ApiOperation(value = "用户注册", notes = "新用户注册")
    public ResultVO registe(@Valid @RequestBody UserInfoParam userInfoParam) throws CommonException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //判断用户名是否被注册
        UserQueryParam userQueryParam = new UserQueryParam();
        userQueryParam.setAccount(userInfoParam.getAccount());
        try {
            UserInfoDTO query = userService.query(userQueryParam);
            if (!Objects.isNull(query)) {
                return ResultVO.error(EmBusinessError.USER_IS_EXIST.getErrCode(), "该用户名已注册");
            }
        } catch (CommonException e) {
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        String redisKey = String.format(RedisConstant.USER_LOGIN_TOKEN + "%S", userInfoParam.getPhone());
        String redisValue = RedisUtil.get(redisKey);
        if (StringUtils.isBlank(redisValue)) {
            return ResultVO.other("验证码已过期,请重新获取");
        }
        if (!redisValue.equals(String.valueOf(userInfoParam.getOtp()))) {
            return ResultVO.error("输入的验证码不正确,请重新输入");
        }
        String pwd = encodeByMD5(userInfoParam.getPwd());
        userInfoParam.setPwd(pwd);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoParam, userInfoDTO);
        Boolean b = userService.add(userInfoDTO);
        if (!b) {
            return ResultVO.error("注册失败");
        }
        RedisUtil.delete(redisKey);
        return ResultVO.success("注册成功");
    }


    @GetMapping("/remove/{uid}")
    @ApiOperation(value = "删除用户信息", notes = "根据用户ID删除用户信息")
    public ResultVO remove(@NotNull @PathVariable(value = "uid") Integer uid) {
        Boolean b = userService.remove(uid);
        if (!b) {
            return ResultVO.error("删除失败");
        }
        return ResultVO.success("删除成功");
    }

    @PostMapping("/modify")
    @ApiOperation(value = "更新用户信息", notes = "根据用户ID更新用户信息")
    public ResultVO modify(@Valid @RequestBody UserInfoParam userInfoParam) throws CommonException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isNotBlank(userInfoParam.getPwd())) {
            String pwd = encodeByMD5(userInfoParam.getPwd());
            userInfoParam.setPwd(pwd);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoParam, userInfoDTO);
        Boolean b = userService.modify(userInfoDTO);
        if (!b) {
            return ResultVO.error("更新失败");
        }
        return ResultVO.success("更新成功");
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "使用用户名、密码进行登录验证")
    public ResultVO login(@Valid @RequestBody UserLoginParam userLoginParam,
                          HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwd = encodeByMD5(userLoginParam.getPwd());
        userLoginParam.setPwd(pwd);
        UserQueryParam userParam = new UserQueryParam();
        BeanUtils.copyProperties(userLoginParam, userParam);
        UserInfoDTO userInfoDTO;
        try {
            userInfoDTO = userService.query(userParam);
        } catch (CommonException e) {
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        if (userInfoDTO == null) {
            return ResultVO.error(EmBusinessError.USER_LOGIN_FAIL.getErrCode(), EmBusinessError.USER_LOGIN_FAIL.getErrMsg());
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfoDTO, userInfoVO);
        String uuid = RandomUtil.uuid();
        RedisUtil.set(String.format(RedisConstant.USER_LOGIN_TOKEN + "%S", uuid), JSON.toJSONString(userInfoVO));
        Cookie cookie = new Cookie(CommonConstant.COOKIE_USER_LOGIN_TOKEN, uuid);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);
        return ResultVO.success("登录成功", userInfoVO);
    }

    @PostMapping("/query")
    @ApiOperation(value = "查询用户信息", notes = "根据用户ID、name查询用户信息")
    @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记")
    public ResultVO query(@Valid @RequestBody UserQueryParam userParam,
                          HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        UserInfoDTO userInfoDTO;
        try {
            userInfoDTO = userService.query(userParam);
        } catch (CommonException e) {
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        if (userInfoDTO == null) {
            return ResultVO.other("未获取到数据,请确认查询条件是否正确");
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfoDTO, userInfoVO);
        return ResultVO.success("获取成功", userInfoVO);
    }

    /**
     * 用户密码进行MD5加密
     *
     * @param pwd 加密前的密码
     * @return EncodeByMD5 使用MD5加密后的密码
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private String encodeByMD5(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(md5.digest(pwd.getBytes("utf-8")));
    }
}
