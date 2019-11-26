package com.hik.seckill.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by wangJinChang on 2019/11/23 17:16
 * 分页视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {

    private Integer totalPage; //总页数

    private Integer total;//记录总数

    private Integer pageNo;//当前页码

    private Integer pageSize;//分页大小

    private List<T> list;//返回数据
}
