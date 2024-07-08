package com.xfc.dict.service;

import com.xfc.dict.dto.DicTypeDTO;
import com.xfc.dict.dto.DicTypeQueryDTO;
import com.xfc.dict.entities.DictionaryType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfc.dict.vo.DictionaryTypeCatalogVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
public interface IDictionaryTypeService extends IService<DictionaryType> {

    List<DictionaryType> getOneLevelList();

    String addType(DicTypeDTO dicTypeDTO);

    String updateType(String id, DicTypeDTO dicTypeDTO);

    String deleteType(String id) throws Exception;

    Map<String, Object> getTwoLevelPage(DicTypeQueryDTO dicTypeQueryDTO);

    DictionaryType getTwoLevelData(String id);

    String getDescriptionById(String id);

    List<DictionaryTypeCatalogVO> getCatalog();

    String getDictionaryTypeLabelByName(String dictionaryTypeName);
}
