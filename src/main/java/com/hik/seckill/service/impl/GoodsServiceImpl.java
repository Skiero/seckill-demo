package com.hik.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hik.seckill.error.CommonException;
import com.hik.seckill.mapper.GoodsMapper;
import com.hik.seckill.model.dto.GoodsInfoDTO;
import com.hik.seckill.model.entity.GoodsEntity;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.model.param.GoodsFuzzyQueryParam;
import com.hik.seckill.model.vo.GoodsInfoVO;
import com.hik.seckill.service.IGoodsService;
import com.hik.seckill.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by wangJinChang on 2019/11/22 16:07
 * 商品业务层实现类
 */
@Service
@Slf4j
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean add(GoodsInfoDTO goodsInfoDTO, Integer userId) {
        GoodsEntity goodsEntity = convertToEntityByDTO(goodsInfoDTO);
        goodsEntity.setCreateId(userId);
        goodsEntity.setCreateTime(new Date());
        int rows = goodsMapper.add(goodsEntity);
        log.debug("GoodsServiceImpl add goods information : goodsInfoDTO is {} , goodsEntity is {} , number of affected rows is {} ", JSON.toJSONString(goodsInfoDTO), JSON.toJSONString(goodsEntity), rows);
        return rows > 0;
    }

    @Override
    public boolean remove(Integer id, Integer userId) {
        int rows = goodsMapper.remove(id, userId);
        log.debug("GoodsServiceImpl remove goods information : goodsId is {} , number of affected rows is {} ", id, rows);
        return rows > 0;
    }

    @Override
    public boolean reduce(Integer id, Integer count, Integer userId) {
        int rows = goodsMapper.reduce(id, count, userId);
        log.debug("GoodsServiceImpl reduce goods count : goodsId is {} , reduce number is {} , number of affected rows is {} ", id, count, rows);
        return rows > 0;
    }

    @Override
    public boolean modify(GoodsInfoDTO goodsInfoDTO, Integer userId) throws CommonException {
        if (Objects.isNull(goodsInfoDTO.getId())) {
            throw new CommonException(EmBusinessError.GOODS_ID_NOT_NULL.getErrCode(), EmBusinessError.GOODS_ID_NOT_NULL.getErrMsg());
        }
        GoodsEntity entity = goodsMapper.queryById(goodsInfoDTO.getId());
        if (Objects.isNull(entity)) {
            throw new CommonException(EmBusinessError.GOODS_NOT_EXIST.getErrCode(), EmBusinessError.GOODS_NOT_EXIST.getErrMsg());
        }
        GoodsEntity goodsEntity = convertToEntityByDTO(goodsInfoDTO);
        goodsEntity.setUpdateId(userId);
        goodsEntity.setUpdateTime(new Date());
        int rows = goodsMapper.modify(goodsEntity);
        log.debug("GoodsServiceImpl modify goods information : goodsInfoDTO is {} , goodsEntity is {} , number of affected rows is {} ", goodsInfoDTO, goodsEntity, rows);
        return rows > 0;
    }

    @Override
    public GoodsInfoVO queryById(Integer id) throws CommonException {
        GoodsEntity goodsEntity = goodsMapper.queryById(id);
        if (Objects.isNull(goodsEntity)) {
            throw new CommonException(EmBusinessError.GOODS_NOT_EXIST.getErrCode(), EmBusinessError.GOODS_NOT_EXIST.getErrMsg());
        }
        GoodsInfoVO goodsInfoVO = convertToVOByEntity(goodsEntity);
        log.debug("GoodsServiceImpl query goods information by id: goodsId is {} , goodsInfoVO is {} ", id, goodsInfoVO);
        return goodsInfoVO;
    }

    @Override
    public List<GoodsInfoVO> queryById(List<Integer> idList) {
        List<GoodsInfoVO> goodsInfoVOList = Lists.newArrayList();
        List<GoodsEntity> goodsEntityList = goodsMapper.queryByIdList(idList);
        if (CollectionUtils.isEmpty(goodsEntityList)) {
            log.debug("GoodsServiceImpl query goods information by idList: goodsIdList is {} , size is {} , result is empty", JSON.toJSONString(idList), idList.size());
            return goodsInfoVOList;
        }
        goodsEntityList.forEach(goodsEntity -> {
            GoodsInfoVO goodsInfoVO = convertToVOByEntity(goodsEntity);
            goodsInfoVOList.add(goodsInfoVO);
        });
        log.debug("GoodsServiceImpl query goods information by idList: goodsIdList is {} , size is {} , result is {} , result size is {}", JSON.toJSONString(idList), idList.size(), JSON.toJSON(goodsInfoVOList), goodsInfoVOList.size());
        return goodsInfoVOList;
    }

    @Override
    public List<GoodsInfoVO> queryByFuzzyQuery(GoodsFuzzyQueryParam goodsFuzzyQueryParam) {
        List<GoodsInfoVO> goodsInfoVOList = Lists.newArrayList();
        List<GoodsEntity> goodsEntityList = goodsMapper.queryByFuzzyQuery(goodsFuzzyQueryParam);
        if (CollectionUtils.isEmpty(goodsEntityList)) {
            log.debug("GoodsServiceImpl query goods information by goodsFuzzyQueryParam: goodsFuzzyQueryParam is {} , result is empty", JSON.toJSONString(goodsFuzzyQueryParam));
            return goodsInfoVOList;
        }
        goodsEntityList.forEach(goodsEntity -> {
            GoodsInfoVO goodsInfoVO = convertToVOByEntity(goodsEntity);
            goodsInfoVOList.add(goodsInfoVO);
        });
        log.debug("GoodsServiceImpl query goods information by goodsFuzzyQueryParam: goodsFuzzyQueryParam is {} , result is {} , result size is {}", JSON.toJSONString(goodsFuzzyQueryParam), JSON.toJSON(goodsInfoVOList), goodsInfoVOList.size());
        return goodsInfoVOList;
    }

    /**
     * DTO转换成entity
     *
     * @param goodsInfoDTO 商品信息数据传递对象
     * @return GoodsEntity
     */
    private GoodsEntity convertToEntityByDTO(GoodsInfoDTO goodsInfoDTO) {
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(goodsInfoDTO, goodsEntity);
        goodsEntity.setUpdateTime(new Date());
        goodsEntity.setGIndexCode(RandomUtil.uuid());
        goodsEntity.setStatus(1);
        return goodsEntity;
    }

    /**
     * entity转换成VO
     *
     * @param goodsEntity 商品实体类信息
     * @return GoodsInfoVO
     */
    private GoodsInfoVO convertToVOByEntity(GoodsEntity goodsEntity) {
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
        BeanUtils.copyProperties(goodsEntity, goodsInfoVO);
        return goodsInfoVO;
    }
}
