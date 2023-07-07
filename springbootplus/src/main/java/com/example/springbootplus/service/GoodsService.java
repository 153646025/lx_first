package com.example.springbootplus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springbootplus.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootplus.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2023-07-02
 */
public interface GoodsService extends IService<Goods> {

    IPage pageC(IPage<Goods> page, Wrapper wrapper);
}
