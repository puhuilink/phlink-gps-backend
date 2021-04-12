package com.xd.pre.flowable.controller;

import java.util.List;

import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xd.pre.flowable.common.BaseFlowableController;
import com.xd.pre.flowable.service.FlowableTaskService;
import com.xd.pre.flowable.vo.IdentityRequest;

/**
 * @author
 * @date
 */
@RestController
@RequestMapping("/flowable/taskIdentityLink")
public class TaskIdentityLinkController extends BaseFlowableController {
    @Autowired
    protected FlowableTaskService flowableTaskService;

    @PreAuthorize("hasAuthority('flowable:taskIdentityLink:list')")
    @GetMapping(value = "/list")
    public R list(@RequestParam String taskId) {
        HistoricTaskInstance task = flowableTaskService.getHistoricTaskInstanceNotNull(taskId);
        List<HistoricIdentityLink> historicIdentityLinks = historyService.getHistoricIdentityLinksForTask(task.getId());
        return R.ok(responseFactory.createTaskIdentityResponseList(historicIdentityLinks));
    }

    @SysOperaLog(descrption = "新增任务授权")
    @PreAuthorize("hasAuthority('flowable:taskIdentityLink:save')")
    @PostMapping(value = "/save")
    public R save(@RequestBody IdentityRequest taskIdentityRequest) {
        flowableTaskService.saveTaskIdentityLink(taskIdentityRequest);
        return R.ok();
    }

    @SysOperaLog(descrption = "删除任务授权")
    @PreAuthorize("hasAuthority('flowable:taskIdentityLink:delete')")
    @DeleteMapping(value = "/delete")
    public R deleteIdentityLink(@RequestParam String taskId, @RequestParam String identityId,
                                     @RequestParam String identityType) {
        flowableTaskService.deleteTaskIdentityLink(taskId, identityId, identityType);
        return R.ok();
    }
}
