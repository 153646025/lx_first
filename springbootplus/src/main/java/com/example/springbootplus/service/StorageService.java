package com.example.springbootplus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootplus.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2023-07-02
 */
public interface StorageService extends IService<Storage> {
    IPage pageC(IPage<Storage> page, Wrapper wrapper);


//    IPage pageC(Page<Storage> page, Wrapper wrapper);
}
