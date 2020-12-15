package com.xd.pre.modules.fence.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.modules.fence.domain.IotFence;
import com.xd.pre.modules.fence.dto.IotFenceDTO;
import com.xd.pre.modules.fence.service.IIotFenceService;
import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 围栏信息表 前端控制器
 * </p>
 *
 * @author phlink
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/iot-fence")
public class IotFenceController {

    @Autowired
    IIotFenceService iIotFenceService;

    /**
     * 添加围栏信息
     *
     * @param iotFence
     * @return
     */
    @SysOperaLog(descrption = "添加围栏信息")
    @PreAuthorize("hasAuthority('iot:fence:add')")
    @PostMapping
    public R add(@RequestBody IotFence iotFence) {
        return R.ok(iIotFenceService.save(iotFence));
    }


    /**
     * 获取围栏信息
     *
     * @return
     */
    @SysOperaLog(descrption = "围栏信息")
    @GetMapping
    @PreAuthorize("hasAuthority('iot:fence:view')")
    public R getList(Page page, IotFence iotFence) {
        return R.ok(iIotFenceService.page(page, Wrappers.query(iotFence)));
    }

    /**
     * 更新围栏
     *
     * @param iotFenceDTO
     * @return
     */
    @SysOperaLog(descrption = "更新围栏")
    @PreAuthorize("hasAuthority('iot:fence:edit')")
    @PutMapping
    public R update(@RequestBody IotFenceDTO iotFenceDTO) {
        return R.ok(iIotFenceService.updateFence(iotFenceDTO));
    }


    /**
     * 根据id删除围栏
     *
     * @param id
     * @return //
     */
    @SysOperaLog(descrption = "根据id删除围栏")
    @PreAuthorize("hasAuthority('iot:fence:del')")
    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") int id) {
        return R.ok(iIotFenceService.removeById(id));
    }

}

