package com.xfc.dict.service;

import com.xfc.common.vo.PageVO;
import com.xfc.dict.dto.DictValueDTO;
import com.xfc.dict.dto.DictValuePageQueryRequestDTO;
import com.xfc.dict.dto.DictValuePageQueryResponseDTO;
import com.xfc.dict.dto.DictValueSavingDTO;
import com.xfc.dict.entities.DictionaryValue;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典值表 服务类
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
public interface IDictionaryValueService extends IService<DictionaryValue> {

    PageVO<DictValuePageQueryResponseDTO> getDictValuePage(DictValuePageQueryRequestDTO queryDTO);

    @Transactional
    void saveDictValue(DictValueSavingDTO dictValueSavingDTO);

    void deleteValue(String id);

    Map<String, Object> getParameters(String typeName);

    String getLabelByValue(String value);

    /**
     * 根据字典类型值查询对应的字典值列表
     *
     * @param dictTypeName 字典类型值
     * @return 字典值列表
     */
    List<DictValueDTO> getDictValuesByDictTypeName(String dictTypeName);

    DictValueDTO getDictByTypeAndName(String dictTypeName, String dictName);

    Boolean isExistValue(String value);

    List<String> getCompleteDataTypes();

    List<String> getCompleteFileTypes();

    String getValueByLabel(String label);

}
