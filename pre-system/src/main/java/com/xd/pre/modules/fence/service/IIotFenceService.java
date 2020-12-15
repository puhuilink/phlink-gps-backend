package com.xd.pre.modules.fence.service;

import com.xd.pre.modules.fence.domain.IotFence;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.fence.dto.IotFenceDTO;

/**
 * <p>
 * 围栏信息表 服务类
 * </p>
 *
 * @author phlink
 * @since 2020-12-09
 */
public interface IIotFenceService extends IService<IotFence> {

     boolean updateFence(IotFenceDTO iotFenceDTO);

}
