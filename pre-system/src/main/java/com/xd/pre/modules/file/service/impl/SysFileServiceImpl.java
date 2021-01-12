package com.xd.pre.modules.file.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.mapper.SysFileMapper;
import com.xd.pre.modules.file.service.ISysFileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {
    @Override
    public SysFile getFileItemByPath(String path) {
        return baseMapper.selectOne(Wrappers.<SysFile>query().lambda().and(i -> i.eq(SysFile::getPath, path)));
    }

    @Override
    public SysFile getFileItemById(Integer fileId) {
        return baseMapper.selectById(fileId);
    }

    @Override
    public void createFileItem(SysFile file) {
        save(file);
    }

    @Override
    public List<SysFile> queryByUserId(Integer userId) {
        return baseMapper.selectList(Wrappers.<SysFile>query().lambda().and(i -> i.eq(SysFile::getUserId, userId)));
    }

    @Override
    public List<SysFile> queryByBiz(String bizType, String bizId) {
        return baseMapper.selectList(Wrappers.<SysFile>query().lambda().and(i -> i.eq(SysFile::getBizId, bizId).eq(SysFile::getBizType, bizType)));
    }

    @Override
    public List<SysFile> queryByBatchId(String batchId) {
        return baseMapper.selectList(Wrappers.<SysFile>query().lambda().and(i -> i.eq(SysFile::getFileBatchId, batchId)));
    }

    @Override
    public void updateFile(String fileBatchId, String bizType, String bizId) {
        update(Wrappers.<SysFile>update().lambda().set(SysFile::getBizId, bizId).set(SysFile::getBizType, bizType).eq(SysFile::getFileBatchId, fileBatchId));
    }
}
