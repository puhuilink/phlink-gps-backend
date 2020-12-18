package com.xd.pre.modules.alarm.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import com.xd.pre.modules.alarm.domain.IotAlarmConfig;
import com.xd.pre.modules.alarm.dto.IotAlarmConfigDTO;
import com.xd.pre.modules.alarm.service.IIotAlarmConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 告警配置表 前端控制器
 * </p>
 *
 * @author zappa
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/iot-alarm-config")
public class IotAlarmConfigController {

    @Autowired
    IIotAlarmConfigService iIotAlarmConfigService;

    /**
     * 添加围栏信息
     *
     * @param iotAlarmConfig
     * @return
     */
    @SysOperaLog(descrption = "添加告警配置信息")
    @PreAuthorize("hasAuthority('iot:alarmConfig:add')")
    @PostMapping
    public R add(@RequestBody IotAlarmConfig iotAlarmConfig) {
        return R.ok(iIotAlarmConfigService.save(iotAlarmConfig));
    }


    /**
     * 获取围栏信息
     *
     * @return
     */
    @SysOperaLog(descrption = "告警配置信息")
    @GetMapping
    @PreAuthorize("hasAuthority('iot:alarmConfig:view')")
    public R getList(Page page, IotAlarmConfigDTO iotAlarmConfigDTO) {
        return R.ok(iIotAlarmConfigService.getIotAlertConfigPageList(page, iotAlarmConfigDTO));
    }

    /**
     * 更新围栏
     *
     * @param iotAlarmConfigDTO
     * @return
     */
    @SysOperaLog(descrption = "更新告警配置")
    @PreAuthorize("hasAuthority('iot:alarmConfig:edit')")
    @PutMapping
    public R update(@RequestBody IotAlarmConfigDTO iotAlarmConfigDTO) {
        return R.ok(iIotAlarmConfigService.updateAlarmConfig(iotAlarmConfigDTO));
    }


    /**
     * 根据id删除配置
     *
     * @param id
     * @return //
     */
    @SysOperaLog(descrption = "根据id删除配置")
    @PreAuthorize("hasAuthority('iot:alarmConfig:del')")
    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") int id) {
        return R.ok(iIotAlarmConfigService.removeById(id));
    }

}

