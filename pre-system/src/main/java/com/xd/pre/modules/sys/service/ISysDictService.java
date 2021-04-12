package com.xd.pre.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.sys.domain.SysDict;
import com.xd.pre.modules.sys.domain.SysDictItem;
import com.xd.pre.modules.sys.dto.DictDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-17
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 修改字典
     * @param dictDto
     * @return
     */
    boolean updateDict(DictDTO dictDto);

    /**
     * 根据主键Id删除字典
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据字典名称查询字段详情
     * @param dictCode
     * @return
     */
    List<SysDictItem> queryDictItemByDictCode(String dictCode);

    /**
     * 根据多个字典名称查询对应字段详情
     * @param dictCodes
     * @return
     */
    Map<String, List<SysDictItem>> queryDictItemByDictCodes(String dictCodes);

}
