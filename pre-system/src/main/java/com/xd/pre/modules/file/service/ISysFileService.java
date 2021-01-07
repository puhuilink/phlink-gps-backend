package com.xd.pre.modules.file.service;

import com.alibaba.fastjson.JSONObject;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.file.domain.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.file.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcc
 * @since 2021-01-06
 */
public interface ISysFileService extends IService<SysFile> {

    JSONObject uploadFile(MultipartFile file, SysFile sysFile) throws IOException;

    R deleteFile(Integer fileId);

    void downloadFile(Integer fileId, HttpServletResponse response) throws IOException;

    R deleteFiles(String batchFileUUID);
}
