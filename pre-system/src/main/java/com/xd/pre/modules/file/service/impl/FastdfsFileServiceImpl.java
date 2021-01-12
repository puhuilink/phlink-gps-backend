package com.xd.pre.modules.file.service.impl;

import com.xd.pre.common.config.FastDFSClient;
import com.xd.pre.common.exception.PreBaseException;
import com.xd.pre.modules.file.domain.FastdfsFileItem;
import com.xd.pre.modules.file.domain.FileItem;
import com.xd.pre.modules.file.domain.LocalFileItem;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.service.FileService;
import com.xd.pre.modules.file.service.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zcc
 * @since 2021-01-06
 */
@Slf4j
public class FastdfsFileServiceImpl implements FileService {
    private final ISysFileService sysFileService;
    private final FastDFSClient fdfsClient;

    public FastdfsFileServiceImpl(ISysFileService sysFileService, FastDFSClient fdfsClient) {
        this.sysFileService = sysFileService;
        this.fdfsClient = fdfsClient;
    }

    protected FastdfsFileItem getFileItem(SysFile file) {
        FastdfsFileItem item = new FastdfsFileItem(fdfsClient);
        item.setName(file.getName());
        item.setPath(file.getPath());
        item.setBizId(file.getBizId());
        item.setBizType(file.getBizType());
        item.setId(file.getId());
        item.setOrgId(file.getOrgId());
        return item;
    }

    protected List<FileItem> getFileItem(List<SysFile> files) {
        List<FileItem> items = new ArrayList<>(files.size());
        for (SysFile file : files) {
            items.add(this.getFileItem(file));
        }
        return items;
    }

    @Override
    public FileItem createFileItem(String name, InputStream in, long size, String bizType, String bizId, Integer userId, Integer orgId, String fileBatchId) throws IOException {
        SysFile sysFile = new SysFile();
        sysFile.setBizId(bizId);
        sysFile.setBizType(bizType);
        sysFile.setUserId(userId);
        sysFile.setOrgId(orgId);
        sysFile.setName(name);
        sysFile.setCreateTime(LocalDateTime.now());
        sysFile.setFileBatchId(fileBatchId);
        //先保存才可以生成主键
        String path = fdfsClient.uploadFile(in, size, name);
        sysFile.setPath(path.substring(path.indexOf("/") + 1));
        sysFileService.createFileItem(sysFile);

        FastdfsFileItem fastdfsFileItem = this.getFileItem(sysFile);
        return fastdfsFileItem;
    }

    @Override
    public boolean removeFile(Integer fileId, String batchFileUUID) {
        SysFile file = sysFileService.getFileItemById(fileId);
        if (!file.getFileBatchId().equals(batchFileUUID)) {
            return false;
        }

        boolean success = fdfsClient.deleteFile(file.getPath());
        if (!success) {
            log.warn("删除文件失败 " + file.getName() + ",id=" + file.getId() + " path=" + file.getPath());
            throw new PreBaseException("删除文件失败 " + file.getName());
        }
        sysFileService.removeById(fileId);
        return true;
    }

    @Override
    public void updateFile(String fileBatchId, String bizType, String bizId) {
        sysFileService.updateFile(fileBatchId, bizType, bizId);
    }

    @Override
    public FileItem loadFileItemByPath(String path) {
        SysFile sysFile = sysFileService.getFileItemByPath(path);
        if (sysFile != null) {
            return getFileItem(sysFile);
        }
        FastdfsFileItem item = new FastdfsFileItem(fdfsClient);
        item.setPath(path);
        item.setName(sysFile.getName());
        return item;
    }

    @Override
    public FileItem getFileItemById(Integer id) {
        return this.getFileItem(sysFileService.getFileItemById(id));
    }

    @Override
    public FileItem getFileItemById(Integer id, String batchFileId) {
        SysFile file = sysFileService.getFileItemById(id);
        if (!file.getFileBatchId().equals(batchFileId)) {
            return null;
        }
        return this.getFileItem(file);
    }

    @Override
    public List<FileItem> queryByBatchId(String batchFileUUID) {
        return this.getFileItem(sysFileService.queryByBatchId(batchFileUUID));
    }

    @Override
    public List<FileItem> queryByBiz(String bizType, String bizId) {
        return this.getFileItem(sysFileService.queryByBiz(bizType, bizId));
    }

    @Override
    public List<FileItem> queryByUserId(Integer userId) {
        return this.getFileItem(sysFileService.queryByUserId(userId));
    }

}
