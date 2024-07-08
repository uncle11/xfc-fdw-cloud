package com.xfc.dict.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xfc.dict.entities.DictionaryType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */

public interface DictionaryTypeMapper extends BaseMapper<DictionaryType> {

    @Select("select a.id,a.type_name as typeName,a.type_description as typeDescription," +
            " a.parent_id as parentId,a.creator,a.create_time as createTime,a.update_time as updateTime,a.type_label as typeLabel" +
            " from dictionary_type a JOIN dictionary_type b on  a.parent_id=b.id ${ew.customSqlSegment} where a.is_deleted = 0 and b.is_deleted = 0 ")
    Page<DictionaryType> getTwoLevelPage(IPage<DictionaryType> page, @Param("ew") QueryWrapper<DictionaryType> queryWrapper);
}
