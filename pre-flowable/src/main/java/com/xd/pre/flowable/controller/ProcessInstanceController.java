package com.xd.pre.flowable.controller;

import com.xd.pre.common.utils.R;
import com.xd.pre.flowable.util.CommonUtil;
import com.xd.pre.flowable.util.ObjectUtils;
import com.xd.pre.flowable.util.SecurityUtils;
import com.xd.pre.flowable.common.BaseFlowableController;
import com.xd.pre.flowable.common.FlowablePage;
import com.xd.pre.flowable.constant.FlowableConstant;
import com.xd.pre.flowable.service.ProcessInstanceService;
import com.xd.pre.flowable.vo.ProcessInstanceDetailResponse;
import com.xd.pre.flowable.vo.query.ProcessInstanceQueryVo;
import com.xd.pre.flowable.vo.ProcessInstanceRequest;
import com.xd.pre.flowable.wapper.CommentListWrapper;
import com.xd.pre.flowable.wapper.ProcInsListWrapper;
import com.xd.pre.log.annotation.SysOperaLog;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.HistoricProcessInstanceQueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date
 */
@RestController
@RequestMapping("/flowable/processInstance")
public class ProcessInstanceController extends BaseFlowableController {

    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

    @Autowired
    private ProcessInstanceService processInstanceService;

    static {
        allowedSortProperties.put(FlowableConstant.ID, HistoricProcessInstanceQueryProperty.PROCESS_INSTANCE_ID_);
        allowedSortProperties.put(FlowableConstant.PROCESS_DEFINITION_ID,
                HistoricProcessInstanceQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortProperties.put(FlowableConstant.PROCESS_DEFINITION_KEY,
                HistoricProcessInstanceQueryProperty.PROCESS_DEFINITION_KEY);
        allowedSortProperties.put(FlowableConstant.BUSINESS_KEY, HistoricProcessInstanceQueryProperty.BUSINESS_KEY);
        allowedSortProperties.put("startTime", HistoricProcessInstanceQueryProperty.START_TIME);
        allowedSortProperties.put("endTime", HistoricProcessInstanceQueryProperty.END_TIME);
        allowedSortProperties.put("duration", HistoricProcessInstanceQueryProperty.DURATION);
        allowedSortProperties.put(FlowableConstant.TENANT_ID, HistoricProcessInstanceQueryProperty.TENANT_ID);
    }

    @PreAuthorize("hasAuthority('flowable:processInstance:list')")
    @GetMapping(value = "/list")
    public R list(ProcessInstanceQueryVo processInstanceQueryVo) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getProcessDefinitionCategory())) {
            query.processDefinitionCategory(processInstanceQueryVo.getProcessDefinitionCategory());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getProcessInstanceId())) {
            query.processInstanceId(processInstanceQueryVo.getProcessInstanceId());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getProcessInstanceName())) {
            query.processInstanceNameLike(processInstanceQueryVo.getProcessInstanceName());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getProcessDefinitionName())) {
            query.processDefinitionName(processInstanceQueryVo.getProcessDefinitionName());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getProcessDefinitionKey())) {
            query.processDefinitionKey(processInstanceQueryVo.getProcessDefinitionKey());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getProcessDefinitionId())) {
            query.processDefinitionId(processInstanceQueryVo.getProcessDefinitionId());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getBusinessKey())) {
            query.processInstanceBusinessKey(processInstanceQueryVo.getBusinessKey());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getInvolvedUser())) {
            query.involvedUser(processInstanceQueryVo.getInvolvedUser());
        }
        if (!processInstanceQueryVo.getFinished().equals(processInstanceQueryVo.getUnfinished())) {
            if (processInstanceQueryVo.getFinished()) {
                query.finished();
            }
            if (processInstanceQueryVo.getUnfinished()) {
                query.unfinished();
            }
        }

        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getSuperProcessInstanceId())) {
            query.superProcessInstanceId(processInstanceQueryVo.getSuperProcessInstanceId());
        }
        if (processInstanceQueryVo.getExcludeSubprocesses()) {
            query.excludeSubprocesses(processInstanceQueryVo.getExcludeSubprocesses());
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getFinishedAfter())) {
            query.finishedAfter(ObjectUtils.convertToDatetime(processInstanceQueryVo.getFinishedAfter()));
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getFinishedBefore())) {
            query.finishedBefore(ObjectUtils.convertToDatetime(processInstanceQueryVo.getFinishedBefore()));
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getStartedAfter())) {
            query.startedAfter(ObjectUtils.convertToDatetime(processInstanceQueryVo.getStartedAfter()));
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getStartedBefore())) {
            query.startedBefore(ObjectUtils.convertToDatetime(processInstanceQueryVo.getStartedBefore()));
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getStartedBy())) {
            query.startedBy(processInstanceQueryVo.getStartedBy());
        }
        // startByMe 覆盖 startedBy
        if (processInstanceQueryVo.getStartedByMe()) {
            query.startedBy(SecurityUtils.getUserId());
        }
        // ccToMe 抄送我
        if (processInstanceQueryVo.getCcToMe()) {
            query.involvedUser(SecurityUtils.getUserId(), FlowableConstant.CC);
        }
        if (CommonUtil.isNotEmptyAfterTrim(processInstanceQueryVo.getTenantId())) {
            query.processInstanceTenantIdLike(processInstanceQueryVo.getTenantId());
        }

        FlowablePage page = this.pageList(processInstanceQueryVo, query, ProcInsListWrapper.class, allowedSortProperties,
                HistoricProcessInstanceQueryProperty.START_TIME);
        return R.ok(page);
    }

    @GetMapping(value = "/listMyInvolvedSummary")
    public R listMyInvolvedSummary(ProcessInstanceQueryVo processInstanceQueryVo) {
        processInstanceQueryVo.setUserId(SecurityUtils.getUserId());
        return R.ok(this.processInstanceService.listMyInvolvedSummary(processInstanceQueryVo));
    }

    @GetMapping(value = "/listMyInvolved")
    public R listMyInvolved(ProcessInstanceQueryVo processInstanceQueryVo) {
        processInstanceQueryVo.setInvolvedUser(SecurityUtils.getUserId());
        return list(processInstanceQueryVo);
    }

    @GetMapping(value = "/listStartedByMe")
    public R listStartedByMe(ProcessInstanceQueryVo processInstanceQueryVo) {
        processInstanceQueryVo.setStartedByMe(true);
        return list(processInstanceQueryVo);
    }

    @GetMapping(value = "/listCcToMe")
    public R listCcToMe(ProcessInstanceQueryVo processInstanceQueryVo) {
        processInstanceQueryVo.setCcToMe(true);
        return list(processInstanceQueryVo);
    }

    @GetMapping(value = "/queryById")
    public R queryById(@RequestParam String processInstanceId) {
        permissionService.validateReadPermissionOnProcessInstance(SecurityUtils.getUserId(), processInstanceId);
        ProcessInstance processInstance = null;
        HistoricProcessInstance historicProcessInstance =
                processInstanceService.getHistoricProcessInstanceById(processInstanceId);
        if (historicProcessInstance.getEndTime() == null) {
            processInstance = processInstanceService.getProcessInstanceById(processInstanceId);
        }
        ProcessInstanceDetailResponse pidr =
                responseFactory.createProcessInstanceDetailResponse(historicProcessInstance, processInstance);
        return R.ok(pidr);
    }

    @SysOperaLog(descrption = "启动流程实例")
    @PostMapping(value = "/start")
    @Transactional(rollbackFor = Exception.class)
    public R start(@RequestBody ProcessInstanceRequest processInstanceRequest) {
        processInstanceService.start(processInstanceRequest);
        return R.ok();
    }

    @SysOperaLog(descrption = "删除流程实例")
    @PreAuthorize("hasAuthority('flowable:processInstance:delete')")
    @DeleteMapping(value = "/delete")
    public R delete(@RequestParam String processInstanceId, @RequestParam(required = false) boolean cascade,
                         @RequestParam(required = false) String deleteReason) {
        processInstanceService.delete(processInstanceId, cascade, deleteReason);
        return R.ok();
    }

    @SysOperaLog(descrption = "挂起流程实例")
    @PreAuthorize("hasAuthority('flowable:processInstance:suspendOrActivate')")
    @PutMapping(value = "/suspend")
    public R suspend(@RequestBody ProcessInstanceRequest processInstanceRequest) {
        processInstanceService.suspend(processInstanceRequest.getProcessInstanceId());
        return R.ok();
    }

    @SysOperaLog(descrption = "激活流程实例")
    @PreAuthorize("hasAuthority('flowable:processInstance:suspendOrActivate')")
    @PutMapping(value = "/activate")
    public R activate(@RequestBody ProcessInstanceRequest processInstanceRequest) {
        processInstanceService.activate(processInstanceRequest.getProcessInstanceId());
        return R.ok();
    }

    @GetMapping(value = "/comments")
    public R comments(@RequestParam String processInstanceId) {
        permissionService.validateReadPermissionOnProcessInstance(SecurityUtils.getUserId(), processInstanceId);
        List<Comment> datas = taskService.getProcessInstanceComments(processInstanceId);
        Collections.reverse(datas);
        return R.ok(this.listWrapper(CommentListWrapper.class, datas));
    }

    @GetMapping(value = "/formData")
    public R formData(@RequestParam String processInstanceId) {
        HistoricProcessInstance processInstance =
                permissionService.validateReadPermissionOnProcessInstance(SecurityUtils.getUserId(), processInstanceId);
        Object renderedStartForm = formService.getRenderedStartForm(processInstance.getProcessDefinitionId());
        Map<String, Object> variables = null;
        if (processInstance.getEndTime() == null) {
            variables = runtimeService.getVariables(processInstanceId);
        } else {
            List<HistoricVariableInstance> hisVals =
                    historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
            variables = new HashMap<>(16);
            for (HistoricVariableInstance variableInstance : hisVals) {
                variables.put(variableInstance.getVariableName(), variableInstance.getValue());
            }
        }
        Map<String, Object> ret = new HashMap<String, Object>(4);
        boolean showBusinessKey = isShowBusinessKey(processInstance.getProcessDefinitionId());
        ret.put("showBusinessKey", showBusinessKey);
        ret.put(FlowableConstant.BUSINESS_KEY, processInstance.getBusinessKey());
        ret.put("renderedStartForm", renderedStartForm);
        ret.put("variables", variables);
        return R.ok(ret);
    }
}
