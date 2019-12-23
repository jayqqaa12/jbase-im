package com.jayqqaa12.im.gateway.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBaseMapper<T> extends BaseMapper<T> {


    /**
     *
     * 批量插入 一些属性
     * 如果为null 默认设置为null
     * 
     * @param batchList
     * @return
     */
    int insertBatchSomeColumn(@Param("list") List<T> batchList);

    
}