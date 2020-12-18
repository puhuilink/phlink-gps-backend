package com.xd.pre.modules.alarm.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.modules.alarm.domain.IotAlarmConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.pre.modules.alarm.dto.IotAlarmConfigDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 告警配置表 Mapper 接口
 * </p>
 *
 * @author zappa
 * @since 2020-12-18
 */
public interface IotAlarmConfigMapper extends BaseMapper<IotAlarmConfig> {

    IPage<IotAlarmConfig> getAlarmConfigPage(Page page, @Param("param") IotAlarmConfigDTO iotAlarmConfigDTO);

}
