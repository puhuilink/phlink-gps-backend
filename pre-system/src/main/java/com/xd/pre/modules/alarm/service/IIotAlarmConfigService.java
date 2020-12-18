package com.xd.pre.modules.alarm.service;

import com.xd.pre.modules.alarm.domain.IotAlarmConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.alarm.dto.IotAlarmConfigDTO;

/**
 * <p>
 * 告警配置表 服务类
 * </p>
 *
 * @author zappa
 * @since 2020-12-18
 */
public interface IIotAlarmConfigService extends IService<IotAlarmConfig> {

    boolean updateAlarmConfig(IotAlarmConfigDTO iotAlarmConfigDTO);

}
