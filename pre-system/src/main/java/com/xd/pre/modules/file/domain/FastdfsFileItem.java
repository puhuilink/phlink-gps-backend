package com.xd.pre.modules.file.domain;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.xd.pre.common.config.FastDFSClient;
import com.xd.pre.common.exception.PreBaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FastdfsFileItem extends PersistFileItem {
    private final FastDFSClient fdfsClient;

    public FastdfsFileItem(FastDFSClient fdfsClient) {
        this.fdfsClient = fdfsClient;
    }

    @Override
    public byte[] getFileByte() {
        this.fileByte = fdfsClient.download(path);
        return fileByte;
    }

    @Override
    public void copy(OutputStream os) {
        byte[] bytes = fdfsClient.download(path);
        try {
            os.write(bytes);
        } catch (IOException e) {
            throw new PreBaseException("下载文件失败" + e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete() {
        return fdfsClient.deleteFile(path);
    }
}
