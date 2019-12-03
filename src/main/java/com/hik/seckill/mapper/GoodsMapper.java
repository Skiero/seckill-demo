package com.hik.seckill.mapper;

import com.hik.seckill.model.entity.GoodsEntity;
import com.hik.seckill.model.param.GoodsFuzzyQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangJinChang on 2019/11/22 14:24
 * 商品相关持久层
 */
@Mapper
public interface GoodsMapper {
    /**
     * 新增商品信息
     *
     * @param goodsEntity 商品信息
     * @return 受影响行数
     */
    int add(GoodsEntity goodsEntity);

    /**
     * 删除商品信息
     *
     * @param id 商品ID
     * @return 受影响行数
     */
    int remove(@Param("id") Integer id, @Param("userId") Integer userId);

    /**
     * 购买商品
     *
     * @param id    商品ID
     * @param count 购买数量
     * @return 受影响行数
     */
    int reduce(@Param("id") Integer id, @Param("count") Integer count, @Param("userId") Integer userId);

    /**
     * 更新商品信息
     *
     * @param goodsEntity 商品信息
     * @return 受影响行数
     */
    int modify(GoodsEntity goodsEntity);

    /**
     * 根据商品ID查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    GoodsEntity queryById(Integer id);

    /**
     * 根据商品ID集合查询商品信息
     *
     * @param idList 商品ID集合
     * @return 商品信息集合
     */
    List<GoodsEntity> queryByIdList(@Param("idList") List<Integer> idList);

    /**
     * 根据商品的模糊查询条件查询商品信息
     *
     * @param goodsFuzzyQueryParam 模糊查询条件
     * @return 商品信息集合
     */
    List<GoodsEntity> queryByFuzzyQuery(GoodsFuzzyQueryParam goodsFuzzyQueryParam);

    /**
     * 获取所有商品信息
     *
     * @return 商品信息集合
     */
    List<GoodsEntity> queryAll();
}
