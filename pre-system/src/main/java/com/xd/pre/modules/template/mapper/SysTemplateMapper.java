package com.xd.pre.modules.template.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.modules.template.domain.SysTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.pre.modules.template.dto.SysTemplateDTO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcc
 * @since 2021-01-13
 */
public interface SysTemplateMapper extends BaseMapper<SysTemplate> {

    IPage<SysTemplate> getTemplatePage(Page page, SysTemplateDTO sysTemplateDTO);
}
