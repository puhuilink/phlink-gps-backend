package com.xd.pre.modules.sys.controller;

import com.sun.tools.hat.internal.util.Misc;
import com.xd.pre.common.config.FastDFSClient;
import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import com.xd.pre.modules.sys.domain.SysDict;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/file")
@RestController
public class FastDFSController {
    @Autowired
    private FastDFSClient fdfsClient;

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value="/upload")
    public R upload(MultipartFile file) throws Exception {
        return R.ok(fdfsClient.uploadFile(file));
    }

    /**
     * 文件删除
     *
     * @param url  url 开头从组名开始
     * @throws Exception
     */
    @GetMapping("/delete")
    public R delete(String url) throws Exception {
        fdfsClient.deleteFile(url);
        return R.ok();
    }

    /**
     * 文件下载
     *
     * @param url  url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @GetMapping(value="/download")
    public void download(String url, HttpServletResponse response) throws Exception {

        byte[] data = fdfsClient.download(url);
        String[] filePath = url.split("/");

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filePath[filePath.length - 1], "UTF-8"));

        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }
}
