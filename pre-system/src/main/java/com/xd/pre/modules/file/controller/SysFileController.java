package com.xd.pre.modules.file.controller;


import com.alibaba.fastjson.JSONObject;
import com.xd.pre.common.config.FastDFSClient;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.dto.FileDTO;
import com.xd.pre.modules.file.service.ISysFileService;
import com.xd.pre.modules.sys.service.ISysDeptService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zcc
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/sys-file")
public class SysFileController {

    @Autowired
    private ISysFileService sysFileService;

    /**
     * 单文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @SneakyThrows
    @PostMapping(value = "/upload")
    public R upload(@Valid FileDTO param) {
        SysFile sysFile = new SysFile();
        sysFile.setName(param.getFile().getOriginalFilename());
        sysFile.setBizId(param.getBizId());
        sysFile.setBizType(param.getBizType());
        sysFile.setFileBatchId(param.getBatchFileUUID());

        return R.ok(sysFileService.uploadFile(param.getFile(), sysFile));
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @SneakyThrows
    @PostMapping(value = "/uploads")
    public R uploads(@Valid FileDTO param) {
        List<JSONObject> dtoList = new ArrayList<>();
        for (MultipartFile file : param.getFiles()) {
            SysFile sysFile = new SysFile();
            sysFile.setName(file.getOriginalFilename());
            sysFile.setBizId(param.getBizId());
            sysFile.setBizType(param.getBizType());
            sysFile.setFileBatchId(param.getBatchFileUUID());
            JSONObject json = sysFileService.uploadFile(file, sysFile);

            dtoList.add(json);
        }

        return R.ok(dtoList);
    }

    /**
     * 文件删除
     *
     * @param url url 开头从组名开始
     * @throws Exception
     */
    @SneakyThrows
    @GetMapping("/deletes")
    public R deletes(String batchFileUUID) {
        return sysFileService.deleteFiles(batchFileUUID);
    }

    /**
     * 文件删除
     *
     * @param url url 开头从组名开始
     * @throws Exception
     */
    @SneakyThrows
    @GetMapping("/delete")
    public R delete(Integer fileId) {
        return sysFileService.deleteFile(fileId);
    }

    /**
     * 文件下载
     *
     * @param url      url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @SneakyThrows
    @GetMapping(value = "/download")
    public void download(Integer fileId, HttpServletResponse response) {
        sysFileService.downloadFile(fileId, response);
    }
}

