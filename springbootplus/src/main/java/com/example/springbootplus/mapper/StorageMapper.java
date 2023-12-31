package com.example.springbootplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springbootplus.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2023-07-02
 */
@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
//    IPage pageC(Page<Storage> page, LambdaQueryWrapper<Storage> lambdaQueryWrapper);


    IPage pageC(IPage<Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
