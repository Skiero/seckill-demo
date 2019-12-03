package com.hik.seckill.service;

import com.hik.seckill.error.CommonException;
import com.hik.seckill.model.dto.GoodsInfoDTO;
import com.hik.seckill.model.param.GoodsFuzzyQueryParam;
import com.hik.seckill.model.vo.GoodsInfoVO;
import com.hik.seckill.model.vo.ItemVO;

import java.util.List;

/**
 * Created by wangJinChang on 2019/11/22 16:07
 * 商品业务层接口
 */
public interface IGoodsService {
    /**
     * 新增商品信息
     *
     * @param GoodsInfoDTO 商品信息传输对象
     * @return true:成功    false:失败
     */
    boolean add(GoodsInfoDTO GoodsInfoDTO, Integer userId);

    /**
     * 删除商品信息
     *
     * @param id 商品ID
     * @return true:成功    false:失败
     */
    boolean remove(Integer id, Integer userId);

    /**
     * 购买商品
     *
     * @param id    商品ID
     * @param count 购买数量
     * @return true:成功    false:失败
     */
    boolean reduce(Integer id, Integer count, Integer userId);

    /**
     * 更新商品信息
     *
     * @param GoodsInfoDTO 商品信息数据传输对象
     * @return true:成功    false:失败
     */
    boolean modify(GoodsInfoDTO GoodsInfoDTO, Integer userId) throws CommonException;

    /**
     * 根据商品ID查询商品信息
     *
     * @param id 商品ID
     * @return 查询成功返回对象, 否则抛出异常
     */
    GoodsInfoVO queryById(Integer id) throws CommonException;

    /**
     * 根据商品ID查询商品信息(秒杀活动)
     *
     * @param id 商品ID
     * @return 查询成功返回对象, 否则抛出异常
     */
    ItemVO queryItemVOById(Integer id);

    /**
     * 根据商品ID集合查询商品信息
     *
     * @param idList 商品ID集合
     * @return 商品信息集合
     */
    List<GoodsInfoVO> queryById(List<Integer> idList);

    /**
     * 根据商品的模糊查询条件查询商品信息
     *
     * @param goodsFuzzyQueryParam 模糊查询条件
     * @return 商品信息集合
     */
    List<GoodsInfoVO> queryByFuzzyQuery(GoodsFuzzyQueryParam goodsFuzzyQueryParam);

    /**
     * 获取所有商品信息
     *
     * @return 商品信息集合
     */
    List<GoodsInfoVO> queryAll();
}
