package com.xd.pre.modules.file.service;

import com.xd.pre.modules.file.domain.FileItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileService {
    public FileItem createFileItem(String name, InputStream in, long size, String bizType, String bizId, Integer userId, Integer orgId, String fileBatchId) throws IOException;

    public boolean removeFile(Integer fileId, String batchFileUUID);

    public void updateFile(String fileBatchId, String bizType, String bizId);

    public FileItem loadFileItemByPath(String path);

    public FileItem getFileItemById(Integer fileId);

    public FileItem getFileItemById(Integer id, String batchFileId);

    public List<FileItem> queryByUserId(Integer userId);

    public List<FileItem> queryByBiz(String bizType, String bizId);

    public List<FileItem> queryByBatchId(String batchFileUUID);
}
