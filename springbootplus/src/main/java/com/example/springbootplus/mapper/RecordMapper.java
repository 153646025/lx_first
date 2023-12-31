package com.example.springbootplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.springbootplus.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2023-07-04
 */

@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    IPage pageC(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
