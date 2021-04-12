package com.xd.pre.flowable.identity;

import com.xd.pre.flowable.mapper.FlowableCommonMapper;
import org.flowable.idm.api.Group;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.flowable.idm.engine.impl.persistence.entity.data.GroupDataManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date
 */
public class CustomGroupEntityManager extends GroupEntityManagerImpl {
    @Resource
    private FlowableCommonMapper flowableCommonMapper;

    public CustomGroupEntityManager(IdmEngineConfiguration idmEngineConfiguration, GroupDataManager groupDataManager) {
        super(idmEngineConfiguration, groupDataManager);
    }

    @Override
    public GroupEntity findById(String entityId) {
        GroupQueryImpl aGroupQueryImpl = (GroupQueryImpl) new GroupQueryImpl().groupId(entityId);
        List<Group> userEntities = flowableCommonMapper.selectGroupByQueryCriteria(aGroupQueryImpl);
        if (userEntities != null && userEntities.size() > 0) {
            return (GroupEntity) userEntities.get(0);
        }
        return null;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query) {
        List<Group> groups = flowableCommonMapper.selectGroupByQueryCriteria(query);
        if (groups == null) {
            return new ArrayList<>();
        } else {
            return groups;
        }
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        return flowableCommonMapper.selectGroupCountByQueryCriteria(query);
    }
}
