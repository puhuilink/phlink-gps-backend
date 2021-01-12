package com.xd.pre.modules.file.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.common.exception.PreBaseException;
import com.xd.pre.common.utils.FileUtil;
import com.xd.pre.modules.file.domain.FileItem;
import com.xd.pre.modules.file.domain.LocalFileItem;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.mapper.SysFileMapper;
import com.xd.pre.modules.file.service.FileService;
import com.xd.pre.modules.file.service.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
public class LocalFileServiceImpl implements FileService {

    private final ISysFileService sysFileService;

    private String root = null;

    public LocalFileServiceImpl(ISysFileService sysFileService, String root) {
        this.root = root;
        this.sysFileService = sysFileService;
        new File(root).mkdir();
    }

    protected LocalFileItem getFileItem(SysFile file) {
        LocalFileItem item = new LocalFileItem(root);
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

    public FileItem createFileTemp(String name) {
        FileItem item = new LocalFileItem(root);
        String fileName = "temp" + File.separator + name + "." + this.suffix();
        item.setPath(fileName);
        item.setName(name);
        return item;
    }

    @Override
    public FileItem createFileItem(String name, InputStream in, long size, String bizType, String bizId, Integer userId, Integer orgId, String fileBatchId) {
        SysFile sysFile = new SysFile();
        sysFile.setBizId(bizId);
        sysFile.setBizType(bizType);
        sysFile.setUserId(userId);
        sysFile.setOrgId(orgId);
        sysFile.setName(name);
        sysFile.setCreateTime(LocalDateTime.now());
        sysFile.setFileBatchId(fileBatchId);
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File dirFile = new File(root + File.separator + dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String fileName = name + "." + UUID.randomUUID().toString();
        String path = dir + File.separator + fileName;
        sysFile.setPath(path);
        sysFileService.createFileItem(sysFile);

        LocalFileItem item = this.getFileItem(sysFile);
        FileUtil.copy(in, item.openOutpuStream());

        return item;
    }

    @Override
    public boolean removeFile(Integer fileId, String batchFileUUID) {
        SysFile file = sysFileService.getFileItemById(fileId);
        if (!file.getFileBatchId().equals(batchFileUUID)) {
            return false;
        }

        FileItem item = this.getFileItem(file);
        boolean success = item.delete();
        if (!success) {
            log.warn("删除文件失败 " + file.getName() + ",id=" + file.getId() + " path=" + file.getPath());
            throw new PreBaseException("删除文件失败 " + file.getName());
        }
        sysFileService.removeById(fileId);
        return true;
    }

    @Override
    public FileItem loadFileItemByPath(String path) {
        SysFile sysFile = sysFileService.getFileItemByPath(path);
        if (sysFile != null) {
            return getFileItem(sysFile);
        }
        LocalFileItem item = new LocalFileItem(root);
        item.setPath(path);
        item.setName(parseTempFileName(path));
        return item;
    }

    private String parseTempFileName(String path) {
        File file = new File(path);
        String name = file.getName();
        //去掉最后的临时标记
        int index = name.lastIndexOf(".");
        return name.substring(0, index);
    }

    private String suffix() {
        return DateUtil.now() + "-" + UUID.randomUUID().toString();
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

    @Override
    public void updateFile(String fileBatchId, String bizType, String bizId) {
        sysFileService.updateFile(fileBatchId, bizType, bizId);
    }
}
