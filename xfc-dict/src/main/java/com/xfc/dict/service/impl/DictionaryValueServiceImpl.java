package com.xfc.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xfc.common.utils.DateUtil;
import com.xfc.common.vo.PageVO;
import com.xfc.dict.dto.DictValueDTO;
import com.xfc.dict.dto.DictValuePageQueryRequestDTO;
import com.xfc.dict.dto.DictValuePageQueryResponseDTO;
import com.xfc.dict.dto.DictValueSavingDTO;
import com.xfc.dict.entities.DictionaryType;
import com.xfc.dict.entities.DictionaryValue;
import com.xfc.dict.mapper.DictionaryTypeMapper;
import com.xfc.dict.mapper.DictionaryValueMapper;
import com.xfc.dict.service.IDictionaryValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfc.dict.vo.ParameterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典值表 服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@Service
public class DictionaryValueServiceImpl extends ServiceImpl<DictionaryValueMapper, DictionaryValue> implements IDictionaryValueService {
    @Resource
    private DictionaryTypeMapper dictionaryTypeMapper;

    /*------------------------------------字典值列表分页查询-开始-----------------------------------------------------*/
    @Override
    public PageVO<DictValuePageQueryResponseDTO> getDictValuePage(DictValuePageQueryRequestDTO dictValuePageQueryRequestDTO) {
        IPage<DictionaryValue> pageQueryResult = this.executePageQuery(dictValuePageQueryRequestDTO);
        List<DictValuePageQueryResponseDTO> dictValuePageQueryResponseDtoList =
                pageQueryResult.getRecords()
                        .stream()
                        .map(this::dictValueToPageQueryResponseDTO)
                        .collect(Collectors.toList());
        return new PageVO<>(pageQueryResult.getTotal(), dictValuePageQueryResponseDtoList);
    }
    private IPage<DictionaryValue> executePageQuery(DictValuePageQueryRequestDTO dictValuePageQueryRequestDTO) {
        LambdaQueryWrapper<DictionaryValue> lambdaQueryWrapper =Wrappers.lambdaQuery();
        lambdaQueryWrapper = lambdaQueryWrapper.eq(StringUtils.hasText(dictValuePageQueryRequestDTO.getTypeId()),DictionaryValue::getTypeId, dictValuePageQueryRequestDTO.getTypeId())
                .like(StringUtils.hasText( dictValuePageQueryRequestDTO.getValueName()),DictionaryValue::getValueName,  dictValuePageQueryRequestDTO.getValueName())
                .orderByAsc(DictionaryValue::getValueSort);
        return this.baseMapper.selectPage(dictValuePageQueryRequestDTO.getPage(), lambdaQueryWrapper);
    }

    private DictValuePageQueryResponseDTO dictValueToPageQueryResponseDTO(DictionaryValue dictionaryValue) {
        DictValuePageQueryResponseDTO dictValuePageQueryResponseDTO = new DictValuePageQueryResponseDTO();
        dictValuePageQueryResponseDTO.setId(dictionaryValue.getId());
        dictValuePageQueryResponseDTO.setValueName(dictionaryValue.getValueName());
        dictValuePageQueryResponseDTO.setValueLabel(dictionaryValue.getValueLabel());
        dictValuePageQueryResponseDTO.setValueSort(dictionaryValue.getValueSort());
        dictValuePageQueryResponseDTO.setTypeName(this.queryDictTypeName(dictionaryValue.getTypeId()));
        dictValuePageQueryResponseDTO.setCreateTime(DateUtil.dateToStr(dictionaryValue.getCreateTime()));
        return dictValuePageQueryResponseDTO;
    }
    private String queryDictTypeName(String dictTypeId) {
        LambdaQueryWrapper<DictionaryType> lambdaQueryWrapper =
                Wrappers.lambdaQuery(DictionaryType.class)
                        .select(DictionaryType::getTypeLabel)
                        .eq(DictionaryType::getId, dictTypeId);
        DictionaryType dictionaryType = this.dictionaryTypeMapper.selectOne(lambdaQueryWrapper);

        if (dictionaryType != null) {
            return dictionaryType.getTypeLabel();
        }

        return "";
    }
    /*------------------------------------字典值列表分页查询-结束-----------------------------------------------------*/


    /*------------------------------------保存字典值（新增/更新）-开始------------------------------------------------*/
    @Transactional("transactionManager")
    @Override
    public void saveDictValue(DictValueSavingDTO dictValueSavingDTO) {
        this.dictValueShouldNotWithSameValue(dictValueSavingDTO);
        DictionaryValue dictionaryValue = this.entityFromSavingDTO(dictValueSavingDTO);
        this.saveOrUpdate(dictionaryValue);
    }
    private void dictValueShouldNotWithSameValue(DictValueSavingDTO dictValueSavingDTO) {
        // 核心的业务规则：不允许出现相同字典值的字典数据

        LambdaQueryWrapper<DictionaryValue> queryWrapper =
                Wrappers.lambdaQuery(DictionaryValue.class)
                        .eq(DictionaryValue::getValueName, dictValueSavingDTO.getValueName());

        if (dictValueSavingDTO.getId() != null) {
            queryWrapper.ne(DictionaryValue::getId, dictValueSavingDTO.getId());
        }

        if(!this.baseMapper.selectList(queryWrapper).isEmpty()) {
            throw new ValidationException("字典值已存在，请检查输入参数");
        }
    }
    private DictionaryValue entityFromSavingDTO(DictValueSavingDTO dictValueSavingDTO) {
        DictionaryValue dictionaryValue = new DictionaryValue();

        dictionaryValue.setId(dictValueSavingDTO.getId());
        dictionaryValue.setTypeId(dictValueSavingDTO.getTypeId());
        dictionaryValue.setValueName(dictValueSavingDTO.getValueName());
        dictionaryValue.setValueLabel(dictValueSavingDTO.getValueLabel());
        dictionaryValue.setValueSort(dictValueSavingDTO.getValueSort());

        return dictionaryValue;
    }
    /*------------------------------------保存字典值（新增/更新）-结束------------------------------------------------*/


    @Override
    public void deleteValue(String id) {
        this.removeById(id);
    }


    //获取某个页面的字典 无children
    @Override
    public Map<String, Object> getParameters(String typeName) {
        List<Map<String, Object>> dictParameters=baseMapper.getDictParameters(typeName);
        Set<Object> typeSet= new HashSet<>();
        Map<String,Object>resParam=new HashMap<>();
        for (Map<String, Object> dictParameter : dictParameters) {
            typeSet.add(dictParameter.get("type_name").toString());
        }
        for (Object o : typeSet) {
            List<ParameterVO> parameterVoList = new ArrayList<>();
            for (Map<String, Object> dictParameter : dictParameters) {
                if(dictParameter.get("type_name").toString().equals(o.toString())){
                    ParameterVO parameterVO=new ParameterVO(dictParameter.get("value_name").toString(),dictParameter.get("value_label").toString());
                    parameterVoList.add(parameterVO);
                }
            }
            resParam.put(o.toString(),parameterVoList);
        }
        return resParam;
    }
    //根据value查label
    @Override
    public String getLabelByValue(String value){
        if(!StringUtils.hasText(value)){
            return null;
        }
        return baseMapper.getLabelByValue(value);
    }

    @Override
    public List<DictValueDTO> getDictValuesByDictTypeName(String dictTypeName) {
        List<DictValueDTO> DictValueDTOList = new ArrayList<>();

        List<DictionaryValue> dictionaryValueList = this.baseMapper.getDictValuesByDictTypeName(dictTypeName);
        for(DictionaryValue dictionaryValue : dictionaryValueList) {
            DictValueDTOList.add(new DictValueDTO(dictionaryValue.getValueLabel(), dictionaryValue.getValueName()));
        }
        return DictValueDTOList;
    }

    @Override
    public DictValueDTO getDictByTypeAndName(String dictTypeName, String dictName) {
        DictionaryValue dictionaryValue = this.baseMapper.getByTypeAndValue(dictTypeName, dictName);
        return new DictValueDTO(dictionaryValue.getValueLabel(), dictionaryValue.getValueName());
    }

    @Override
    public Boolean isExistValue(String value){
        LambdaQueryWrapper<DictionaryValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictionaryValue::getValueName,value);
        DictionaryValue dictionaryValue = baseMapper.selectOne(queryWrapper);
        return dictionaryValue != null;
    }

    @Override
    public  List<String> getCompleteDataTypes() {
        return baseMapper.getCompleteDataTypes();
    }

    @Override
    public  List<String> getCompleteFileTypes() {
        return baseMapper.getCompleteFileTypes();
    }

    @Override
    public String getValueByLabel(String label) {
        if(!StringUtils.hasText(label)){
            return null;
        }
        return baseMapper.getValueByLabel(label);
    }


}

