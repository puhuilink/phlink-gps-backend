package com.xd.pre.flowable.controller;

import com.xd.pre.common.utils.R;
import com.xd.pre.flowable.common.RedissonLock;
import com.xd.pre.flowable.util.CommonUtil;
import com.xd.pre.flowable.util.ObjectUtils;
import com.xd.pre.flowable.util.SecurityUtils;
import com.xd.pre.flowable.common.BaseFlowableController;
import com.xd.pre.flowable.common.FlowablePage;
import com.xd.pre.flowable.constant.FlowableConstant;
import com.xd.pre.flowable.service.FlowableTaskService;
import com.xd.pre.flowable.util.FlowableUtils;
import com.xd.pre.flowable.vo.*;
import com.xd.pre.flowable.vo.query.TaskQueryVo;
import com.xd.pre.flowable.wapper.TaskListWrapper;
import com.xd.pre.flowable.wapper.TaskTodoListWrapper;
import com.xd.pre.log.annotation.SysOperaLog;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.HistoricTaskInstanceQueryProperty;
import org.flowable.task.service.impl.TaskQueryProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date
 */
@RestController
@RequestMapping("/flowable/task")
public class TaskController extends BaseFlowableController {
    @Autowired
    protected FlowableTaskService flowableTaskService;

    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();
    private static Map<String, QueryProperty> allowedSortPropertiesTodo = new HashMap<>();

    static {
        allowedSortProperties.put("deleteReason", HistoricTaskInstanceQueryProperty.DELETE_REASON);
        allowedSortProperties.put("duration", HistoricTaskInstanceQueryProperty.DURATION);
        allowedSortProperties.put("endTime", HistoricTaskInstanceQueryProperty.END);
        allowedSortProperties.put(FlowableConstant.EXECUTION_ID, HistoricTaskInstanceQueryProperty.EXECUTION_ID);
        allowedSortProperties.put("taskInstanceId", HistoricTaskInstanceQueryProperty.HISTORIC_TASK_INSTANCE_ID);
        allowedSortProperties.put(FlowableConstant.PROCESS_DEFINITION_ID,
                HistoricTaskInstanceQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortProperties.put(FlowableConstant.PROCESS_INSTANCE_ID,
                HistoricTaskInstanceQueryProperty.PROCESS_INSTANCE_ID);
        allowedSortProperties.put("assignee", HistoricTaskInstanceQueryProperty.TASK_ASSIGNEE);
        allowedSortProperties.put(FlowableConstant.TASK_DEFINITION_KEY,
                HistoricTaskInstanceQueryProperty.TASK_DEFINITION_KEY);
        allowedSortProperties.put("description", HistoricTaskInstanceQueryProperty.TASK_DESCRIPTION);
        allowedSortProperties.put("dueDate", HistoricTaskInstanceQueryProperty.TASK_DUE_DATE);
        allowedSortProperties.put(FlowableConstant.NAME, HistoricTaskInstanceQueryProperty.TASK_NAME);
        allowedSortProperties.put("owner", HistoricTaskInstanceQueryProperty.TASK_OWNER);
        allowedSortProperties.put("priority", HistoricTaskInstanceQueryProperty.TASK_PRIORITY);
        allowedSortProperties.put(FlowableConstant.TENANT_ID, HistoricTaskInstanceQueryProperty.TENANT_ID_);
        allowedSortProperties.put("startTime", HistoricTaskInstanceQueryProperty.START);

        allowedSortPropertiesTodo.put(FlowableConstant.PROCESS_DEFINITION_ID, TaskQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortPropertiesTodo.put(FlowableConstant.PROCESS_INSTANCE_ID, TaskQueryProperty.PROCESS_INSTANCE_ID);
        allowedSortPropertiesTodo.put(FlowableConstant.TASK_DEFINITION_KEY, TaskQueryProperty.TASK_DEFINITION_KEY);
        allowedSortPropertiesTodo.put("dueDate", TaskQueryProperty.DUE_DATE);
        allowedSortPropertiesTodo.put(FlowableConstant.NAME, TaskQueryProperty.NAME);
        allowedSortPropertiesTodo.put("priority", TaskQueryProperty.PRIORITY);
        allowedSortPropertiesTodo.put(FlowableConstant.TENANT_ID, TaskQueryProperty.TENANT_ID);
        allowedSortPropertiesTodo.put("createTime", TaskQueryProperty.CREATE_TIME);
    }

    protected HistoricTaskInstanceQuery createHistoricTaskInstanceQuery(TaskQueryVo taskQueryVo) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskId())) {
            query.taskId(taskQueryVo.getTaskId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceId())) {
            query.processInstanceId(taskQueryVo.getProcessInstanceId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceBusinessKey())) {
            query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessInstanceBusinessKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionKey())) {
            query.processDefinitionKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionId())) {
            query.processDefinitionId(taskQueryVo.getProcessDefinitionId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionName())) {
            query.processDefinitionNameLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getExecutionId())) {
            query.executionId(taskQueryVo.getExecutionId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskName())) {
            query.taskNameLike(ObjectUtils.convertToLike(taskQueryVo.getTaskName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDescription())) {
            query.taskDescriptionLike(ObjectUtils.convertToLike(taskQueryVo.getTaskDescription()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDefinitionKey())) {
            query.taskDefinitionKeyLike(ObjectUtils.convertToLike(taskQueryVo.getTaskDefinitionKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskAssignee())) {
            query.taskAssignee(taskQueryVo.getTaskAssignee());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskOwner())) {
            query.taskOwner(taskQueryVo.getTaskOwner());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskInvolvedUser())) {
            query.taskInvolvedUser(taskQueryVo.getTaskInvolvedUser());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskPriority())) {
            query.taskPriority(taskQueryVo.getTaskPriority());
        }
        Boolean finished = CommonUtil.isEmptyDefault(taskQueryVo.getFinished(), false);
        Boolean unfinished = CommonUtil.isEmptyDefault(taskQueryVo.getUnfinished(), false);
        if (!finished.equals(unfinished)) {
            if (finished) {
                query.finished();
            }
            if (unfinished) {
                query.unfinished();
            }
        }
        Boolean processFinished = CommonUtil.isEmptyDefault(taskQueryVo.getProcessFinished(), false);
        Boolean processUnfinished = CommonUtil.isEmptyDefault(taskQueryVo.getProcessUnfinished(), false);
        if (!processFinished.equals(processUnfinished)) {
            if (processFinished) {
                query.processFinished();
            }
            if (processUnfinished) {
                query.processUnfinished();
            }
        }

        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskParentTaskId())) {
            query.taskParentTaskId(taskQueryVo.getTaskParentTaskId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTenantId())) {
            query.taskTenantId(taskQueryVo.getTenantId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCandidateUser())) {
            query.taskCandidateUser(taskQueryVo.getTaskCandidateUser());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCandidateGroup())) {
            query.taskCandidateGroup(taskQueryVo.getTaskCandidateGroup());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCandidateGroupIn())) {
            query.taskCandidateGroupIn(Arrays.asList(taskQueryVo.getTaskCandidateGroupIn().split(",")));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueAfter())) {
            query.taskDueAfter(ObjectUtils.convertToDate(taskQueryVo.getTaskDueAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueBefore())) {
            query.taskDueBefore(ObjectUtils.convertToDate(taskQueryVo.getTaskDueBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedAfter())) {
            query.taskCreatedAfter(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedBefore())) {
            query.taskCreatedBefore(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCompletedAfter())) {
            query.taskCompletedAfter(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCompletedAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCompletedBefore())) {
            query.taskCompletedBefore(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCompletedBefore()));
        }

        return query;
    }

    protected TaskQuery createTaskQuery(TaskQueryVo taskQueryVo) {
        TaskQuery query = taskService.createTaskQuery();
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceId())) {
            query.processInstanceId(taskQueryVo.getProcessInstanceId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskName())) {
            query.taskNameLike(ObjectUtils.convertToLike(taskQueryVo.getTaskName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceBusinessKey())) {
            query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessInstanceBusinessKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionKey())) {
            query.processDefinitionKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionId())) {
            query.processDefinitionId(taskQueryVo.getProcessDefinitionId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionName())) {
            query.processDefinitionNameLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueAfter())) {
            query.taskDueAfter(ObjectUtils.convertToDate(taskQueryVo.getTaskDueAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueBefore())) {
            query.taskDueBefore(ObjectUtils.convertToDate(taskQueryVo.getTaskDueBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedAfter())) {
            query.taskCreatedAfter(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedBefore())) {
            query.taskCreatedBefore(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTenantId())) {
            query.taskTenantId(taskQueryVo.getTenantId());
        }
        Boolean suspended = CommonUtil.isEmptyDefault(taskQueryVo.getSuspended(), false);
        Boolean active = CommonUtil.isEmptyDefault(taskQueryVo.getActive(), false);
        if (!suspended.equals(active)) {
            if (suspended) {
                query.suspended();
            }
            if (active) {
                query.active();
            }
        }
        return query;
    }

    @PreAuthorize("hasAuthority('flowable:task:list')")
    @GetMapping(value = "/list")
    public R list(TaskQueryVo taskQueryVo) {
        HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(taskQueryVo);
        FlowablePage page = this.pageList(taskQueryVo, query, TaskListWrapper.class, allowedSortProperties,
                HistoricTaskInstanceQueryProperty.START);
        return R.ok(page);
    }

    @GetMapping(value = "/listDone")
    public R listDone(TaskQueryVo taskQueryVo) {
        HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(taskQueryVo);
        query.finished().or().taskAssignee(SecurityUtils.getUserId()).taskOwner(SecurityUtils.getUserId()).endOr();
        FlowablePage page = this.pageList(taskQueryVo, query, TaskListWrapper.class, allowedSortProperties,
                HistoricTaskInstanceQueryProperty.START);
        return R.ok(page);
    }

    @GetMapping(value = "/listTodo")
    public R listTodo(TaskQueryVo taskQueryVo) {
        String userId = SecurityUtils.getUserId();
        TaskQuery query = createTaskQuery(taskQueryVo);
        query.taskCategory(FlowableConstant.CATEGORY_TODO);
        query.or().taskCandidateOrAssigned(userId).taskOwner(userId).endOr();
        FlowablePage page = this.pageList(taskQueryVo, query, TaskTodoListWrapper.class, allowedSortProperties,
                TaskQueryProperty.CREATE_TIME);
        return R.ok(page);
    }

    @GetMapping(value = "/listToRead")
    public R listToRead(TaskQueryVo taskQueryVo) {
        String userId = SecurityUtils.getUserId();
        TaskQuery query = createTaskQuery(taskQueryVo);
        query.taskCategory(FlowableConstant.CATEGORY_TO_READ);
        query.or().taskAssignee(userId).taskOwner(userId).endOr();
        FlowablePage page = this.pageList(taskQueryVo, query, TaskTodoListWrapper.class, allowedSortProperties,
                TaskQueryProperty.CREATE_TIME);
        return R.ok(page);
    }

    @GetMapping(value = "/queryById")
    public R queryById(@RequestParam String taskId) {
        TaskResponse task = flowableTaskService.getTask(taskId);
        return R.ok(task);
    }

    @SysOperaLog(descrption = "修改任务")
    @PreAuthorize("hasAuthority('flowable:task:update')")
    @PutMapping(value = "/update")
    public R update(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        TaskResponse task = flowableTaskService.updateTask(taskUpdateRequest);
        return R.ok(task);
    }

    @SysOperaLog(descrption = "删除任务")
    @PreAuthorize("hasAuthority('flowable:task:delete')")
    @DeleteMapping(value = "/delete")
    public R delete(@RequestParam String taskId) {
        flowableTaskService.deleteTask(taskId);
        return R.ok();
    }

    @SysOperaLog(descrption = "转办任务")
    @PutMapping(value = "/assign")
    public R assign(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.assignTask(taskRequest);
        return R.ok();
    }

    @PutMapping(value = "/delegate")
    public R delegate(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.delegateTask(taskRequest);
        return R.ok();
    }

    @SysOperaLog(descrption = "委派任务")
    @PutMapping(value = "/claim")
    @RedissonLock(lockIndexs = 0, fieldNames = "taskId")
    public R claim(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.claimTask(taskRequest);
        return R.ok();
    }

    @SysOperaLog(descrption = "认领任务")
    @PutMapping(value = "/unclaim")
    public R unclaim(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.unclaimTask(taskRequest);
        return R.ok();
    }

    @PutMapping(value = "/complete")
    public R complete(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.completeTask(taskRequest);
        return R.ok();
    }

    @SysOperaLog(descrption = "取消认领任务")
    @PutMapping(value = "/stopProcessInstance")
    public R stopProcessInstance(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.stopProcessInstance(taskRequest);
        return R.ok();
    }

    @SysOperaLog(descrption = "完成任务")
    @GetMapping(value = "/renderedTaskForm")
    public R renderedTaskForm(@RequestParam String taskId) {
        permissionService.validateReadPermissionOnTask2(taskId, SecurityUtils.getUserId(), true, true);
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
        return R.ok(renderedTaskForm);
    }

    @SysOperaLog(descrption = "结束流程实例")
    @GetMapping(value = "/executeTaskData")
    public R executeTaskData(@RequestParam String taskId) {
        Task task = permissionService.validateReadPermissionOnTask2(taskId, SecurityUtils.getUserId(), true, true);

        Process process = repositoryService.getBpmnModel(task.getProcessDefinitionId()).getMainProcess();
        UserTask userTask = (UserTask) process.getFlowElement(task.getTaskDefinitionKey(), true);
        if (userTask == null) {
            throw new FlowableObjectNotFoundException("Can not find userTask by id " + task.getTaskDefinitionKey());
        }

        String startFormKey = formService.getStartFormKey(task.getProcessDefinitionId());
        String taskFormKey = formService.getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        Object renderedStartForm = formService.getRenderedStartForm(task.getProcessDefinitionId());
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
        Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());

        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        Boolean showBusinessKey = isShowBusinessKey(task.getProcessDefinitionId());

        ExecuteTaskDataVo executeTaskDataVo = new ExecuteTaskDataVo();
        executeTaskDataVo.setStartUserId(processInstance.getStartUserId());
        executeTaskDataVo.setStartFormKey(startFormKey);
        executeTaskDataVo.setTaskFormKey(taskFormKey);
        executeTaskDataVo.setRenderedStartForm(renderedStartForm);
        executeTaskDataVo.setRenderedTaskForm(renderedTaskForm);
        executeTaskDataVo.setVariables(variables);
        executeTaskDataVo.setShowBusinessKey(showBusinessKey);
        // 当前任务是发起者
        if (FlowableConstant.INITIATOR.equals(task.getTaskDefinitionKey())) {
            executeTaskDataVo.setInitiator(true);
        }

        String buttons = FlowableUtils.getFlowableAttributeValue(userTask, FlowableConstant.BUTTONS);
        if (buttons != null) {
            executeTaskDataVo.setButtons(buttons.split(","));
        }

        historyService.createHistoricVariableInstanceQuery().processInstanceId("ss").variableNameLike("ss");
        return R.ok(executeTaskDataVo);
    }

    @GetMapping(value = "/backNodes")
    public R backNodes(@RequestParam String taskId) {
        List<FlowNodeResponse> datas = flowableTaskService.getBackNodes(taskId);
        return R.ok(datas);
    }

    @SysOperaLog(descrption = "退回任务")
    @PutMapping(value = "/back")
    public R back(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.backTask(taskRequest);
        return R.ok();
    }

    @PutMapping(value = "/read")
    public R read(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.readTask(taskRequest);
        return R.ok();
    }
}
