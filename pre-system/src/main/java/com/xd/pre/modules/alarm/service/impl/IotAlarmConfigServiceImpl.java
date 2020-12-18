package com.xd.pre.modules.alarm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.modules.alarm.domain.IotAlarmConfig;
import com.xd.pre.modules.alarm.dto.IotAlarmConfigDTO;
import com.xd.pre.modules.alarm.mapper.IotAlarmConfigMapper;
import com.xd.pre.modules.alarm.service.IIotAlarmConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 告警配置表 服务实现类
 * </p>
 *
 * @author zappa
 * @since 2020-12-18
 */
@Service
public class IotAlarmConfigServiceImpl extends ServiceImpl<IotAlarmConfigMapper, IotAlarmConfig> implements IIotAlarmConfigService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAlarmConfig(IotAlarmConfigDTO iotAlarmConfigDTO) {
        IotAlarmConfig iotAlarmConfig = new IotAlarmConfig();
        BeanUtil.copyProperties(iotAlarmConfigDTO, iotAlarmConfig);
        return updateById(iotAlarmConfig);
    }

    @Override
    public IPage<IotAlarmConfig> getIotAlertConfigPageList(Page page, IotAlarmConfigDTO iotAlarmConfigDTO) {
        IPage<IotAlarmConfig> iPage = baseMapper.getAlarmConfigPage(page, iotAlarmConfigDTO);
        return iPage;
    }

}
