package com.xd.pre.modules.template.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import com.xd.pre.modules.alarm.domain.IotAlarmConfig;
import com.xd.pre.modules.alarm.dto.IotAlarmConfigDTO;
import com.xd.pre.modules.alarm.service.IIotAlarmConfigService;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.service.FileService;
import com.xd.pre.modules.file.service.ISysFileService;
import com.xd.pre.modules.template.domain.SysTemplate;
import com.xd.pre.modules.template.dto.SysTemplateDTO;
import com.xd.pre.modules.template.service.ISysTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcc
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/sys-template")
public class SysTemplateController {
    @Autowired
    ISysTemplateService templateService;

    /**
     * 添加围栏信息
     *
     * @param iotAlarmConfig
     * @return
     */
    @SysOperaLog(descrption = "添加模板信息")
    @PreAuthorize("hasAuthority('sys:template:add')")
    @PostMapping
    public R add(@RequestBody SysTemplate sysTemplate) {
        return R.ok(templateService.insertTemplate(sysTemplate));
    }


    /**
     * 获取围栏信息
     *
     * @return
     */
    @SysOperaLog(descrption = "获取模板信息")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:template:view')")
    public R getList(Page page, SysTemplateDTO sysTemplateDTO) {
        return R.ok(templateService.getTemplatePageList(page, sysTemplateDTO));
    }

    /**
     * 根据id删除配置
     *
     * @param id
     * @return //
     */
    @SysOperaLog(descrption = "根据id删除模板")
    @PreAuthorize("hasAuthority('sys:template:del')")
    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") int id) {
        return R.ok(templateService.removeTemplate(id));
    }

}

