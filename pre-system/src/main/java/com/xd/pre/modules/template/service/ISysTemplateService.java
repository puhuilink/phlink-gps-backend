package com.xd.pre.modules.template.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.modules.alarm.domain.IotAlarmConfig;
import com.xd.pre.modules.alarm.dto.IotAlarmConfigDTO;
import com.xd.pre.modules.template.domain.SysTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.template.dto.SysTemplateDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcc
 * @since 2021-01-13
 */
public interface ISysTemplateService extends IService<SysTemplate> {

    IPage<SysTemplate> getTemplatePageList(Page page, SysTemplateDTO sysTemplateDTO);

    boolean removeTemplate(int id);

    boolean insertTemplate(SysTemplate sysTemplate);
}
