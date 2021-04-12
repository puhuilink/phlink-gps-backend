package com.xd.pre.flowable.controller;

import java.util.List;

import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import org.flowable.job.api.Job;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xd.pre.flowable.common.BaseFlowableController;

/**
 * @author
 * @date
 */
@RestController
@RequestMapping("/flowable/processDefinitionJob")
public class ProcessDefinitionJobController extends BaseFlowableController {

    @PreAuthorize("hasAuthority('flowable:procDefJob:list')")
    @GetMapping(value = "/list")
    public List<Job> list(@RequestParam String processDefinitionId) {
        return managementService.createTimerJobQuery().processDefinitionId(processDefinitionId).list();
    }

    @SysOperaLog(descrption = "新增流程定义定时任务")
    @PreAuthorize("hasAuthority('flowable:procDefJob:delete')")
    @DeleteMapping(value = "/delete")
    @Transactional(rollbackFor = Exception.class)
    public R deleteJob(@RequestParam String jobId) {
        managementService.deleteTimerJob(jobId);
        return R.ok();
    }
}
