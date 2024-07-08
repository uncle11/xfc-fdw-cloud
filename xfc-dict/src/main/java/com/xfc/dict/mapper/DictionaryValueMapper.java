package com.xfc.dict.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xfc.dict.entities.DictionaryValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfc.dict.vo.DictValueVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典值表 Mapper 接口
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
public interface DictionaryValueMapper extends BaseMapper<DictionaryValue> {


    @Select("select a.id, a.value_name as valueName,a.create_time as createTime,a.value_label as valueLabel,a.value_sort as valueSort,\n" +
            "b.type_name as typeName from dictionary_value a LEFT JOIN dictionary_type b on a.type_id=b.id WHERE a.is_deleted = 0 and b.is_deleted = 0 AND ${ew.customSqlSegment}")
    Page<DictValueVO> getValuePage(IPage<DictValueVO> page, @Param("ew") QueryWrapper<DictValueVO> queryWrapper);

    @Select("select a.id, a.value_name as valueName,a.create_time as createTime,a.creator_name As creator,b.type_description as typeDescription,a.value_label as valueLabel,a.value_sort as valueSort,\n" +
            "b.type_name as typeName from dictionary_value a LEFT JOIN dictionary_type b on a.type_id=b.id WHERE a.is_deleted = 0 and b.is_deleted = 0 AND ${ew.customSqlSegment}")
    DictValueVO getValue(@Param("ew")QueryWrapper<DictValueVO> queryWrapper);


    @Select("select a.value_name,a.value_label,a.type_name from  dictionary_type d JOIN  (select v.value_name,v.value_label,t.type_name,t.parent_id from dictionary_value v,dictionary_type t where v.type_id=t.id and  v.is_deleted = 0 and t.is_deleted = 0)a on a.parent_id=d.id where d.type_name =#{typeName} AND d.is_deleted = 0")
    List<Map<String, Object>> getDictParameters(@Param("typeName") String typeName);

    @Select("select value_label from dictionary_value where value_name=#{value} AND is_deleted = 0 ")
    String getLabelByValue(@Param("value") String value);

    @Select("SELECT dv.value_name, dv.value_label \n" +
            "FROM dictionary_value dv, dictionary_type dt \n" +
            "WHERE dv.type_id = dt.id AND dt.type_name = #{typeName} AND dv.is_deleted = 0and dt.is_deleted = 0" +
            "ORDER BY dv.value_sort;")
    List<DictionaryValue> getDictValuesByDictTypeName(@Param("typeName") String typeName);

    @Select("SELECT dv.value_name, dv.value_label \n" +
            "FROM dictionary_value dv, dictionary_type dt \n" +
            "WHERE dv.type_id = dt.id AND dt.type_name = #{dictTypeName} AND dv.value_name = #{dictName} AND dv.is_deleted = 0and dt.is_deleted = 0 ")
    DictionaryValue getByTypeAndValue(@Param("dictTypeName") String dictTypeName,
                                      @Param("dictName") String dictName);


    @Select("select distinct s.value_name from   (select v.value_name,t.type_name,t.parent_id from dictionary_value v join dictionary_type t on v.type_id=t.id where v.is_deleted = 0 and t.is_deleted = 0) s join dictionary_type f on s.parent_id=f.\"id\" where f.type_name='complete' and s.type_name='dataType' AND f.is_deleted = 0 ")
    List<String> getCompleteDataTypes();

    @Select("select distinct s.value_name from   (select v.value_name,t.type_name,t.parent_id from dictionary_value v join dictionary_type t on v.type_id=t.id where v.is_deleted = 0 and t.is_deleted = 0) s join dictionary_type f on s.parent_id=f.\"id\" where f.type_name='complete' and s.type_name='fileType' AND f.is_deleted = 0 ")
    List<String> getCompleteFileTypes();

    @Select("select value_name from dictionary_value where value_label=#{label} AND is_deleted = 0 ")
    String getValueByLabel(String label);
}

