package com.xd.pre.modules.template.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.modules.alarm.domain.IotAlarmConfig;
import com.xd.pre.modules.alarm.dto.IotAlarmConfigDTO;
import com.xd.pre.modules.file.domain.SysFile;
import com.xd.pre.modules.file.service.FileService;
import com.xd.pre.modules.file.service.ISysFileService;
import com.xd.pre.modules.template.domain.SysTemplate;
import com.xd.pre.modules.template.dto.SysTemplateDTO;
import com.xd.pre.modules.template.mapper.SysTemplateMapper;
import com.xd.pre.modules.template.service.ISysTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zcc
 * @since 2021-01-13
 */
@Service
public class SysTemplateServiceImpl extends ServiceImpl<SysTemplateMapper, SysTemplate> implements ISysTemplateService {

    @Autowired
    private ISysFileService sysFileService;


    @Autowired
    @Qualifier("fastdfsFileService")
    private FileService fileService;

    @Override
    public IPage<SysTemplate> getTemplatePageList(Page page, SysTemplateDTO sysTemplateDTO) {
        IPage<SysTemplate> iPage = baseMapper.getTemplatePage(page, sysTemplateDTO);
        return iPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeTemplate(int id) {
        SysFile file = sysFileService.getFileItemById(id);
        fileService.removeFile(id, file.getFileBatchId());
        if (fileService.queryByBatchId(file.getFileBatchId()).size() == 0){
            remove(Wrappers.<SysTemplate>update().lambda().set(SysTemplate::getDelFlag, '1').set(SysTemplate::getBatchId, "").eq(SysTemplate::getBatchId, file.getFileBatchId()));
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertTemplate(SysTemplate sysTemplate) {
        sysFileService.updateFile(sysTemplate.getBatchId(),sysTemplate.getBizType(),sysTemplate.getBizId());
        return save(sysTemplate);
    }
}
