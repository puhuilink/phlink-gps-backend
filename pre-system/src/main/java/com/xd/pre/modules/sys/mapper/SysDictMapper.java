package com.xd.pre.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.pre.modules.sys.domain.SysDict;
import com.xd.pre.modules.sys.domain.SysDictItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-17
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    @Select("SELECT sdi.id,sdi.item_text,sdi.item_value FROM sys_dict AS sd LEFT JOIN sys_dict_item AS sdi ON sd.id = sdi.dict_id WHERE sd.dict_code=#{dictCode}")
    List<SysDictItem> queryDictItemByDictCode(String dictCode);

    @Select("<script>" +
            "      SELECT sdi.id,sd.dict_code,sdi.item_text,sdi.item_value" +
            "        FROM sys_dict AS sd" +
            "   LEFT JOIN sys_dict_item AS sdi" +
            "          ON sd.id = sdi.dict_id" +
            "       WHERE sd.dict_code in" +
            "      <foreach item='item' collection='dictCodes' open='(' separator=',' close=')' index=''>" +
            "          #{item}" +
            "      </foreach>" +
            "</script>")
    List<SysDictItem> queryDictItemByDictCodes(@Param("dictCodes") String[] dictCodes);

}
