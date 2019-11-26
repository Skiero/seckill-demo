package com.hik.seckill.controller;

import com.google.common.collect.Lists;
import com.hik.seckill.error.CommonException;
import com.hik.seckill.model.dto.GoodsInfoDTO;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.model.param.GoodsFuzzyQueryParam;
import com.hik.seckill.model.param.GoodsInfoParam;
import com.hik.seckill.model.vo.GoodsInfoVO;
import com.hik.seckill.model.vo.ResultVO;
import com.hik.seckill.service.IGoodsService;
import com.hik.seckill.utils.CasUtil;
import com.hik.seckill.validator.ValidationResult;
import com.hik.seckill.validator.ValidatorImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by wangJinChang on 2019/11/22 16:56
 * 商品控制器层
 */
@RestController
@RequestMapping("/goods")
@Api(tags = "商品控制器层")
@Validated
@Slf4j
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ValidatorImpl validator;

    /**
     * 新增商品信息
     *
     * @param goodsInfoParam 商品信息入参
     * @return ResultVO
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    public ResultVO addGoodsInformation(@Valid @RequestBody GoodsInfoParam goodsInfoParam) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("GoodsController addGoodsInformation error , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        ValidationResult validate = validator.validate(goodsInfoParam);
        if (validate.isHasErrors()) {
            return ResultVO.error(EmBusinessError.VALIDATION_ERROR.getErrCode(), validate.getErrMsg());
        }
        boolean b = goodsService.add(convertToDTOByParam(goodsInfoParam), optional.get());
        if (!b) {
            return ResultVO.error(EmBusinessError.USER_SERVICE_ERROR.getErrCode(), "新增失败");
        }
        return ResultVO.success("新增成功");
    }

    /**
     * 根据商品ID删除商品信息
     *
     * @param id 商品ID
     * @return ResultVO
     */
    @GetMapping("/remove/{id}")
    @ApiOperation(value = "根据商品ID删除商品信息", notes = "根据商品ID删除商品信息")
    public ResultVO removeGoodsInformationById(@Min(value = 1, message = "商品ID不能小于1") @PathVariable(value = "id") Integer id) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("GoodsController addGoodsInformation error , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        boolean b = goodsService.remove(id, optional.get());
        if (!b) {
            return ResultVO.error(EmBusinessError.USER_SERVICE_ERROR.getErrCode(), "新增失败");
        }
        return ResultVO.success("删除成功");
    }

    /**
     * 根据商品ID购买商品
     *
     * @param id    商品ID
     * @param count 购买数量
     * @return ResultVO
     */
    @GetMapping("/reduce/{id}/{count}")
    @ApiOperation(value = "根据商品ID购买商品", notes = "根据商品ID购买商品")
    public ResultVO purchaseGoods(@Min(value = 1, message = "商品ID不能小于1") @PathVariable("id") Integer id,
                                  @Min(value = 1, message = "购买数量不能小于1") @PathVariable("count") Integer count) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("GoodsController addGoodsInformation error , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        boolean b = goodsService.reduce(id, count, optional.get());
        if (!b) {
            return ResultVO.error(EmBusinessError.USER_SERVICE_ERROR.getErrCode(), "购买失败");
        }
        return ResultVO.success("购买成功");
    }

    /**
     * 根据商品ID更新商品信息
     *
     * @param goodsInfoParam 商品信息入参
     * @return ResultVO
     */
    @PostMapping("/modify")
    @ApiOperation(value = "根据商品ID更新商品信息", notes = "根据商品ID更新商品信息")
    public ResultVO modifyGooodsInformationById(@Valid @RequestBody GoodsInfoParam goodsInfoParam) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("GoodsController addGoodsInformation error , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        boolean b;
        try {
            b = goodsService.modify(convertToDTOByParam(goodsInfoParam), optional.get());
        } catch (CommonException e) {
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        if (!b) {
            return ResultVO.error(EmBusinessError.USER_SERVICE_ERROR.getErrCode(), "更新失败");
        }
        return ResultVO.success("更新成功");
    }

    /**
     * 根据商品ID查询商品信息
     *
     * @param id 商品ID
     * @return ResultVO
     */
    @GetMapping("/query/{id}")
    @ApiOperation(value = "根据商品ID查询商品信息", notes = "根据商品ID查询商品信息")
    public ResultVO queryGoodsInformationById(@Min(1) @PathVariable("id") Integer id) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("GoodsController addGoodsInformation error , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        GoodsInfoVO goodsInfoVO;
        try {
            goodsInfoVO = goodsService.queryById(id);
        } catch (CommonException e) {
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        return ResultVO.success("查询成功", goodsInfoVO);
    }

    /**
     * 根据商品ID集合查询商品信息
     *
     * @param ids 商品ID(使用<,>分割)
     * @return ResultVO
     */
    @GetMapping("/query")
    @ApiOperation(value = "根据商品ID集合查询商品信息", notes = "根据商品ID集合查询商品信息")
    public ResultVO queryGoodsInformationByIdList(@Pattern(regexp = "^((?! ).)*$", message = "输入的商品ID不能包含空串") @RequestParam("ids") String ids) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("GoodsController addGoodsInformation error , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        List<String> strings = Arrays.asList(ids.split(","));
        List<Integer> idList = Lists.newArrayList();
        strings.forEach(s -> {
            try {
                int i = Integer.parseInt(s);
                idList.add(i);
            } catch (Exception e) {
                log.error(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrCode() + EmBusinessError.VALIDATION_ERROR.getErrMsg());
            }

        });
        if (CollectionUtils.isEmpty(idList)) {
            return ResultVO.error(EmBusinessError.VALIDATION_ERROR.getErrCode(), EmBusinessError.VALIDATION_ERROR.getErrMsg());
        }
        List<GoodsInfoVO> goodsInfoVOList = goodsService.queryById(idList);
        return ResultVO.success("查询成功", goodsInfoVOList);
    }

    /**
     * 根据查询条件模糊查询商品信息
     *
     * @param goodsFuzzyQueryParam 查询条件
     * @return ResultVO
     */
    @PostMapping("/fuzzyQuery")
    @ApiOperation(value = "根据查询条件模糊查询商品信息", notes = "根据查询条件模糊查询商品信息")
    public ResultVO queryByFuzzyQuery(@Valid @RequestBody GoodsFuzzyQueryParam goodsFuzzyQueryParam) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("GoodsController addGoodsInformation error , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        List<GoodsInfoVO> goodsInfoVOList = goodsService.queryByFuzzyQuery(goodsFuzzyQueryParam);
        return ResultVO.success("查询成功", goodsInfoVOList);
    }

    private GoodsInfoDTO convertToDTOByParam(GoodsInfoParam goodsInfoParam) {
        GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();
        BeanUtils.copyProperties(goodsInfoParam, goodsInfoDTO);
        return goodsInfoDTO;
    }
}
