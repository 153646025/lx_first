package com.example.springbootplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.springbootplus.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootplus.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2023-07-02
 */

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage pageC(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
