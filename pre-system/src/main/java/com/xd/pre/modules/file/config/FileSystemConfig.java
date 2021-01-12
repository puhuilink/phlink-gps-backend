package com.xd.pre.modules.file.config;

import com.xd.pre.common.config.FastDFSClient;
import com.xd.pre.modules.file.service.FileService;
import com.xd.pre.modules.file.service.ISysFileService;
import com.xd.pre.modules.file.service.impl.FastdfsFileServiceImpl;
import com.xd.pre.modules.file.service.impl.LocalFileServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
@ConditionalOnMissingBean(FileService.class)
public class FileSystemConfig {
    @Autowired
    Environment env;

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private FastDFSClient fdfsClient;

    @Bean(name = "localFileService")
    public FileService localFileService() {
        String root = env.getProperty("localFile.root");
        if (StringUtils.isEmpty(root)) {
            String userDir = System.getProperty("user.dir");
            root = userDir + File.separator + "filesystem";
        }
        File f = new File(root);
        if (!f.exists()) {
            f.mkdirs();
        }
        return new LocalFileServiceImpl(sysFileService, root);
    }

    @Bean(name = "fastdfsFileService")
    public FileService fastdfsFileService() {
        return new FastdfsFileServiceImpl(sysFileService, fdfsClient);
    }
}
