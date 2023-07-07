package com.example.springbootplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.entity.Goodstype;
import com.example.springbootplus.entity.Storage;
import com.example.springbootplus.mapper.GoodsMapper;
import com.example.springbootplus.mapper.GoodstypeMapper;
import com.example.springbootplus.mapper.StorageMapper;
import com.example.springbootplus.service.GoodstypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-07-02
 */
@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements GoodstypeService {

    @Autowired
    private GoodstypeMapper goodstypeMapper;

    @Override
    public IPage pageC(IPage<Goodstype> page, Wrapper wrapper) {

        return goodstypeMapper.pageC(page,wrapper);
    }
}
