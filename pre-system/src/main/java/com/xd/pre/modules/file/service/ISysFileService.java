package com.xd.pre.modules.file.service;

import com.alibaba.fastjson.JSONObject;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.file.domain.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.file.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    R deleteFiles(String batchFileUUID);

    SysFile getFileItemById(Integer fileId);
    List<SysFile> queryByBatchId(String batchFileUUID);
    List<SysFile> queryByBiz(String bizType, String bizId);
    List<SysFile> queryByUserId(Integer userId);
    void updateFile(String fileBatchId,String bizType,String bizId);

    void downloadFile(Integer fileId, HttpServletResponse response) throws IOException;
}
