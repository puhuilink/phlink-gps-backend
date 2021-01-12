package com.xd.pre.modules.file.service;

import com.alibaba.fastjson.JSONObject;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.file.domain.FileItem;
import com.xd.pre.modules.file.domain.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.file.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zcc
 * @since 2021-01-06
 */
public interface ISysFileService extends IService<SysFile> {
    SysFile getFileItemByPath(String path);

    SysFile getFileItemById(Integer id);

    void createFileItem(SysFile file);

    List<SysFile> queryByUserId(Integer userId);

    List<SysFile> queryByBiz(String bizType, String bizId);

    List<SysFile> queryByBatchId(String batchId);

    void updateFile(String fileBatchId, String bizType, String bizId);
}
