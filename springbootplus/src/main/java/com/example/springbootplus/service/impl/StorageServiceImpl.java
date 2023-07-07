package com.example.springbootplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.entity.Storage;
import com.example.springbootplus.mapper.StorageMapper;
import com.example.springbootplus.service.StorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {


    @Resource
    private StorageMapper storageMapper;

    @Override
    public IPage pageC(IPage<Storage> page, Wrapper wrapper) {
        return storageMapper.pageC(page,wrapper);
    }


//
//    @Override
//    public IPage pageC(Page<Storage> page, Wrapper wrapper) {
//
//        return storageMapper.pageC(page,wrapper);
//
//    }

}
