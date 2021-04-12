package com.xd.pre.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.sys.domain.SysDict;
import com.xd.pre.modules.sys.domain.SysDictItem;
import com.xd.pre.modules.sys.dto.DictDTO;
import com.xd.pre.modules.sys.mapper.SysDictMapper;
import com.xd.pre.modules.sys.service.ISysDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-17
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDict(DictDTO dictDto) {
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(dictDto, sysDict);
        return updateById(sysDict);
    }


    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public List<SysDictItem> queryDictItemByDictCode(String dictCode) {

        return baseMapper.queryDictItemByDictCode(dictCode);
    }

    @Override
    public Map<String, List<SysDictItem>> queryDictItemByDictCodes(String dictCodes) {

        List<SysDictItem> SysDictItems = baseMapper.queryDictItemByDictCodes(strToArray(dictCodes));

        Map<String, List<SysDictItem>> dictItemGroupBy = SysDictItems.stream().collect(Collectors.groupingBy(SysDictItem::getDictCode));

        return dictItemGroupBy;
    }

    public String[] strToArray(String str) {
        StringTokenizer st = new StringTokenizer(str, ",");
        String[] strArray = new String[st.countTokens()];
        int strLeng = st.countTokens();
        for (int i=0; i<strLeng; i++) {
            strArray[i] = st.nextToken();
        }
        return strArray;
    }

}
