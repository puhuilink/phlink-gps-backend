package com.xd.pre.flowable.controller;

import java.util.Arrays;

import javax.validation.Valid;

import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.flowable.entity.FlowableForm;
import com.xd.pre.flowable.service.FlowableFormService;

/**
 * 流程Controller
 *
 * @author 庄金明
 */
@RestController
@RequestMapping("/flowable/form")
public class FlowableFormController extends BaseController {
    @Autowired
    private FlowableFormService flowableFormService;

    /**
     * 自定义查询列表
     *
     * @param flowableForm
     * @param current
     * @param size
     * @return
     */
    @PreAuthorize("hasAuthority('flowable:form:list')")
    @GetMapping(value = "/list")
    public R list(FlowableForm flowableForm, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<FlowableForm> pageList = flowableFormService.list(new Page<FlowableForm>(current, size), flowableForm);
        return R.ok(pageList);
    }

    @PreAuthorize("hasAuthority('flowable:form:list')")
    @GetMapping(value = "/queryById")
    public R queryById(@RequestParam String id) {
        FlowableForm flowableForm = flowableFormService.getById(id);
        return R.ok(flowableForm);
    }

    /**
     * @param flowableForm
     * @return
     * @功能：新增
     */
    @SysOperaLog(descrption = "新增流程表单")
    @PreAuthorize("hasAuthority('flowable:form:save')")
    @PostMapping(value = "/save")
    public R save(@Valid @RequestBody FlowableForm flowableForm) {
        flowableFormService.save(flowableForm);
        return R.ok();
    }

    /**
     * @param flowableForm
     * @return
     * @功能：修改
     */
    @SysOperaLog(descrption = "修改流程表单")
    @PreAuthorize("hasAuthority('flowable:form:update')")
    @PutMapping(value = "/update")
    public R update(@Valid @RequestBody FlowableForm flowableForm) {
        flowableFormService.updateById(flowableForm);
        return R.ok();
    }

    /**
     * @param ids
     * @return
     * @功能：批量删除
     */
    @SysOperaLog(descrption = "删除流程表单")
    @PreAuthorize("hasAuthority('flowable:form:delete')")
    @DeleteMapping(value = "/delete")
    public R delete(@RequestParam String ids) {
        if (ids == null || ids.trim().length() == 0) {
            return R.error("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            flowableFormService.removeByIds(Arrays.asList(idsArr));
        } else {
            flowableFormService.removeById(idsArr[0]);
        }
        return R.ok();
    }
}
