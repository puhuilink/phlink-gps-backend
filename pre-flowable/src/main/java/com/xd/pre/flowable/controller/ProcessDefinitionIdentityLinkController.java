package com.xd.pre.flowable.controller;

import java.util.List;

import com.xd.pre.common.utils.R;
import com.xd.pre.log.annotation.SysOperaLog;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.identitylink.api.IdentityLink;
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
import com.xd.pre.flowable.service.ProcessDefinitionService;
import com.xd.pre.flowable.vo.IdentityRequest;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@RestController
@RequestMapping("/flowable/processDefinitionIdentityLink")
public class ProcessDefinitionIdentityLinkController extends BaseFlowableController {
    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @PreAuthorize("hasAuthority('flowable:processDefinitionIdentityLink:list')")
    @GetMapping(value = "/list")
    public R list(@RequestParam String processDefinitionId) {
        ProcessDefinition processDefinition = processDefinitionService.getProcessDefinitionById(processDefinitionId);
        List<IdentityLink> identityLinks =
                repositoryService.getIdentityLinksForProcessDefinition(processDefinition.getId());
        return R.ok(responseFactory.createIdentityResponseList(identityLinks));
    }

    @SysOperaLog(descrption = "新增流程定义授权")
    @PreAuthorize("hasAuthority('flowable:processDefinitionIdentityLink:save')")
    @PostMapping(value = "/save")
    public R save(@RequestBody IdentityRequest identityRequest) {
        processDefinitionService.saveProcessDefinitionIdentityLink(identityRequest);
        return R.ok();
    }

    @SysOperaLog(descrption = "删除流程定义授权")
    @PreAuthorize("hasAuthority('flowable:processDefinitionIdentityLink:delete')")
    @DeleteMapping(value = "/delete")
    public R delete(@RequestParam String processDefinitionId, @RequestParam String identityId,
                         @RequestParam String identityType) {
        processDefinitionService.deleteProcessDefinitionIdentityLink(processDefinitionId, identityId, identityType);
        return R.ok();
    }
}
