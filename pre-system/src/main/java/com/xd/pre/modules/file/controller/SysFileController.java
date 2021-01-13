package com.xd.pre.modules.file.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.xd.pre.common.config.FastDFSClient;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.file.domain.FileItem;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.dto.FileDTO;
import com.xd.pre.modules.file.service.FileService;
import com.xd.pre.modules.file.service.ISysFileService;
import com.xd.pre.modules.sys.domain.SysUser;
import com.xd.pre.modules.sys.service.ISysDeptService;
import com.xd.pre.modules.sys.service.ISysUserService;
import com.xd.pre.security.util.SecurityUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.time.LocalDateTime;
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
    @Qualifier("fastdfsFileService")
//    @Qualifier("localFileService")二选一
    private FileService fileService;

    @Autowired
    private ISysUserService userService;
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
        SysUser user = userService.findByUserInfoName(SecurityUtil.getUser().getUsername());
        FileItem item = fileService.createFileItem(param.getFile().getOriginalFilename(), param.getFile().getInputStream(),
                param.getFile().getSize(), param.getBizType(), param.getBizId(), user.getUserId(), user.getDeptId(), param.getBatchFileUUID());

        JSONObject result = new JSONObject();
        result.put("path", item.getPath());
        result.put("name", item.getName());
        result.put("id", item.getId());

        return R.ok(result);
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
            SysUser user = userService.findByUserInfoName(SecurityUtil.getUser().getUsername());
            FileItem item = fileService.createFileItem(file.getOriginalFilename(), file.getInputStream(),
                    file.getSize(), param.getBizType(), param.getBizId(), user.getUserId(), user.getDeptId(), param.getBatchFileUUID());

            JSONObject result = new JSONObject();
            result.put("path", item.getPath());
            result.put("name", item.getName());
            result.put("id", item.getId());

            dtoList.add(result);
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
    @GetMapping("/delete")
    public R delete(Integer fileId, String batchFileUUID) {
        return fileService.removeFile(fileId, batchFileUUID) ? R.ok() : R.error();
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
        FileItem item = fileService.getFileItemById(fileId);
        response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(item.getName(),"UTF-8"));
        item.copy(response.getOutputStream());
    }
}

