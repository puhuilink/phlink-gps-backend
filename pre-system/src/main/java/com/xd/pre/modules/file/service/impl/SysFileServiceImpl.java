package com.xd.pre.modules.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xd.pre.common.config.FastDFSClient;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.dto.FileDTO;
import com.xd.pre.modules.file.mapper.SysFileMapper;
import com.xd.pre.modules.file.service.ISysFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.sys.domain.SysMenu;
import com.xd.pre.modules.sys.domain.SysUser;
import com.xd.pre.modules.sys.service.ISysUserService;
import com.xd.pre.security.PreSecurityUser;
import com.xd.pre.security.util.SecurityUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zcc
 * @since 2021-01-06
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Autowired
    private FastDFSClient fdfsClient;

    @Autowired
    private ISysUserService userService;

    private static final String FILE_NOT_EXIST = "文件不存在";

    @Override
    public JSONObject uploadFile(MultipartFile file, SysFile sysFile) throws IOException {
        String path = fdfsClient.uploadFile(file);
        sysFile.setPath(path.substring(path.indexOf("/") + 1));
        sysFile.setCreateTime(LocalDateTime.now());
        SysUser user = userService.findByUserInfoName(SecurityUtil.getUser().getUsername());
        sysFile.setUserId(user.getUserId());
        sysFile.setOrgId(user.getDeptId());
        save(sysFile);

        JSONObject result = new JSONObject();
        result.put("path", sysFile.getPath());
        result.put("name", sysFile.getName());
        result.put("id", sysFile.getId());
        return result;
    }

    @Override
    public R deleteFile(Integer fileId) {
        LambdaQueryWrapper<SysFile> queryWrapper = Wrappers.<SysFile>query().lambda();
        SysFile file = getOne(queryWrapper.select(SysFile::getId, SysFile::getPath).and(i -> i.eq(SysFile::getId, fileId)));
        if (file == null) {
            return R.error(FILE_NOT_EXIST);
        } else if (fdfsClient.deleteFile(file.getPath())) {
            return removeById(file.getId()) ? R.ok() : R.error();
        } else {
            return R.error();
        }
    }

    @Override
    public R deleteFiles(String batchFileUUID) {
        LambdaQueryWrapper<SysFile> queryWrapper = Wrappers.<SysFile>query().lambda();
        List<SysFile> files = list(queryWrapper.select(SysFile::getId, SysFile::getPath).and(i -> i.eq(SysFile::getFileBatchId, batchFileUUID)));
        for (SysFile file : files) {
            try {
                if (file != null) {
                    fdfsClient.deleteFile(file.getPath());
                    removeById(file.getId());
                }
            } catch (Exception e) {
                log.error("删除过程出现错误：" + e);
            }
        }
        return R.ok();
    }

    @Override
    public void downloadFile(Integer fileId, HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<SysFile> queryWrapper = Wrappers.<SysFile>query().lambda();
        SysFile file = getOne(queryWrapper.select(SysFile::getPath, SysFile::getName).and(i -> i.eq(SysFile::getId, fileId)));
        if (file == null) {
            SecurityUtil.writeJavaScript(R.error(FILE_NOT_EXIST), response);
        }
        byte[] data = fdfsClient.download(file.getPath());
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }
}
