package com.example.springbootplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springbootplus.entity.Goods;
import com.example.springbootplus.mapper.GoodsMapper;
import com.example.springbootplus.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public IPage pageC(IPage<Goods> page, Wrapper wrapper) {
        return goodsMapper.pageC(page,wrapper);
    }
}
