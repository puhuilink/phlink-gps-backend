package com.xd.pre.modules.fence.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xd.pre.modules.fence.domain.IotFence;
import com.xd.pre.modules.fence.dto.IotFenceDTO;
import com.xd.pre.modules.fence.mapper.IotFenceMapper;
import com.xd.pre.modules.fence.service.IIotFenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.sys.domain.SysDict;
import com.xd.pre.modules.sys.dto.DictDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 围栏信息表 服务实现类
 * </p>
 *
 * @author phlink
 * @since 2020-12-09
 */
@Service
public class IotFenceServiceImpl extends ServiceImpl<IotFenceMapper, IotFence> implements IIotFenceService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateFence(IotFenceDTO iotFenceDTO) {
        IotFence iotFence = new IotFence();
        BeanUtil.copyProperties(iotFenceDTO, iotFence);
        return updateById(iotFence);
    }

}
