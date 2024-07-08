package com.xfc.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xfc.common.utils.DateUtil;
import com.xfc.dict.dto.DicTypeDTO;
import com.xfc.dict.dto.DicTypeQueryDTO;
import com.xfc.dict.dto.DictTypePageQueryRequestDTO;
import com.xfc.dict.entities.DictionaryType;
import com.xfc.dict.entities.DictionaryValue;
import com.xfc.dict.mapper.DictionaryTypeMapper;
import com.xfc.dict.mapper.DictionaryValueMapper;
import com.xfc.dict.service.IDictionaryTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfc.dict.vo.DictionaryTypeCatalogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@Service
@Slf4j
public class DictionaryTypeServiceImpl extends ServiceImpl<DictionaryTypeMapper, DictionaryType> implements IDictionaryTypeService {


    @Autowired
    DictionaryValueMapper dictionaryValueMapper;
    @Override
    public List<DictionaryType> getOneLevelList() {
        QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("parent_id")
                .or()
                .eq("parent_id","")
                .orderByDesc("create_time");
        List<DictionaryType>dictionaryTypes=baseMapper.selectList(queryWrapper);
        return dictionaryTypes;
    }

    @Override
    public String addType(DicTypeDTO dicTypeDTO) {
        if(dicTypeDTO==null|| !StringUtils.hasText(dicTypeDTO.getTypeName())||!StringUtils.hasText(dicTypeDTO.getTypeDescription())){
            throw new ValidationException("传入参数不可为空");
        }
        if(typeNameExist(dicTypeDTO.getTypeName())){
            throw new ValidationException("字典类型已经存在");
        }
//            if(userInfoDTO==null|| StringUtils.isEmpty(userInfoDTO.getUserName())){
//                throw new ValidationException("用户信息为空");
//            }
        DictionaryType dictionaryType=new DictionaryType();
        dictionaryType.setTypeLabel(dicTypeDTO.getTypeLabel());
        dictionaryType.setTypeName(dicTypeDTO.getTypeName());
        dictionaryType.setTypeDescription(dicTypeDTO.getTypeDescription());
        dictionaryType.setParentId(dicTypeDTO.getParentId());
        baseMapper.insert(dictionaryType);
//            return ResponseStructure.instance(UPDATE_RETURN_201.getCode(), "新增字典类型成功");
        return "新增字典类型成功";
    }

    @Override
    public String updateType(String id, DicTypeDTO dicTypeDTO) {
        if(dicTypeDTO==null|| !StringUtils.hasText(id)){
            throw new ValidationException("传入参数不可为空");
        }
        DictionaryType dictionaryType=baseMapper.selectById(id);
        if(dictionaryType==null){
            throw new ValidationException("字典类型不存在");
        }
        if(StringUtils.hasText(dicTypeDTO.getTypeName())&&!dicTypeDTO.getTypeName().equals(dictionaryType.getTypeName())){
            if(typeNameExist(dicTypeDTO.getTypeName())){
                throw new ValidationException("字典类型已经存在");
            }

            dictionaryType.setTypeName(dicTypeDTO.getTypeName());
        }
        dictionaryType.setTypeLabel(dicTypeDTO.getTypeLabel());
        dictionaryType.setTypeDescription(dicTypeDTO.getTypeDescription());
        baseMapper.updateById(dictionaryType);
        return "更新字典类型成功";
    }

    /**
     * 如删除父节点则级联删除对应子节点
     * @param id
     * @return
     */
    @Transactional
    @Override
    public String deleteType(String id) {
        if(!StringUtils.hasText(id)){
            throw new ValidationException("传入参数不可为空");
//                return ResponseStructure.instance(CONFLICT_RETURN_409.getCode(),"传入参数不可为空");
        }
        DictionaryType dictionaryType=baseMapper.selectById(id);
        if(dictionaryType==null){
            throw new ValidationException("字典类型不存在");
//                return ResponseStructure.failed("字典类型不存在");
        }
        //为二级菜单删除二级菜单及下属字典值
        if(StringUtils.hasText(dictionaryType.getParentId())){
            baseMapper.deleteById(id);
            delValus(id);
        }else {
            //为一级菜单则删除二级菜单则二级菜单对应字典值
            baseMapper.deleteBatchIds(getNodeIds(dictionaryType.getId()));
            delValus(id);
        }
        return "删除字典类型成功";

    }

    @Override
    public Map<String, Object> getTwoLevelPage(DicTypeQueryDTO dicTypeQueryDTO) {
        Map<String,Object> resPage=new HashMap<>();

        if(dicTypeQueryDTO==null||dicTypeQueryDTO.getPage()==null){
            throw new ValidationException("传入参数不可为空");
//                return ResponseStructure.instance(CONFLICT_RETURN_409.getCode(),"传入参数不可为空");
        }
        QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.hasText(dicTypeQueryDTO.getParentId())){
            queryWrapper
                    .like(StringUtils.hasText(dicTypeQueryDTO.getTypeName()),"a.type_name",dicTypeQueryDTO.getTypeName())
                    .orderByDesc("a.create_time");
            Page<DictionaryType> typePage=baseMapper.getTwoLevelPage(dicTypeQueryDTO.getPage(),queryWrapper);
            resPage.put("total",typePage.getTotal());
            resPage.put("items",typePage.getRecords());
            return resPage;
        }else {
            queryWrapper.eq("parent_id",dicTypeQueryDTO.getParentId());
            queryWrapper.like(StringUtils.hasText(dicTypeQueryDTO.getTypeName()),"type_name",dicTypeQueryDTO.getTypeName());
            queryWrapper.orderByDesc("create_time");
            Page typePage= (Page) baseMapper.selectPage(dicTypeQueryDTO.getPage(), queryWrapper);
            resPage.put("total",typePage.getTotal());
            List<DictTypePageQueryRequestDTO> list=new ArrayList<>(typePage.getRecords().size());
            for (Object record : typePage.getRecords()) {
                DictionaryType res= (DictionaryType) record;
                DictTypePageQueryRequestDTO dictTypePageQueryRequestDTO=new DictTypePageQueryRequestDTO();
                BeanUtils.copyProperties(res, dictTypePageQueryRequestDTO);
                dictTypePageQueryRequestDTO.setCreateTime(DateUtil.dateToStr(res.getCreateTime()));
                list.add(dictTypePageQueryRequestDTO);
            }
            resPage.put("items",list);
            return resPage;
        }
    }

    @Override
    public DictionaryType getTwoLevelData(String id) {
        DictionaryType dictionaryType=baseMapper.selectById(id);
        return dictionaryType;
    }

    @Override
    public String getDescriptionById(String id) {
        DictionaryType data = this.getOne(new LambdaQueryWrapper<DictionaryType>()
                .eq(DictionaryType::getId, id));
        if(data == null){
            return "id不存在";
//                return ResponseStructure.instance(409,"id不存在");
        }
        String typeDescription = data.getTypeDescription();
        return typeDescription;
    }

    @Override
    public List<DictionaryTypeCatalogVO> getCatalog() {
        //返回给前端的实体类
        List<DictionaryTypeCatalogVO> data = new ArrayList<>();

        //查询parent_id为null的节点（没有父节点） 肯定有父节点
        List<DictionaryType> rootNodes = this.list(new LambdaQueryWrapper<DictionaryType>().isNull(DictionaryType::getParentId));

        //用于存放所有根节点id
        ArrayList<String> rootNodesIds = new ArrayList<>();

        for (DictionaryType rootNode : rootNodes) {
            rootNodesIds.add(rootNode.getId());
            data.add(new DictionaryTypeCatalogVO(rootNode.getId(),null,rootNode.getTypeLabel()));
        }
        //用于存放根节点以外的节点
        List<DictionaryTypeCatalogVO> otherNodes = new ArrayList<>();

        QueryWrapper<DictionaryType>queryWrapper = new QueryWrapper<>();

        List<DictionaryType> dictionaryTypeList = this.list(queryWrapper);

        for (DictionaryType dictionaryType : dictionaryTypeList) {
            //如果节点不是根节点
            if (!rootNodesIds.contains(dictionaryType.getId())) {
                otherNodes.add(new DictionaryTypeCatalogVO(dictionaryType.getId()
                        ,dictionaryType.getParentId(),dictionaryType.getTypeLabel()));
            }
        }
        for (DictionaryTypeCatalogVO temp : data) {
            //构造跟节点的子节点树
            temp = buildUserTree(otherNodes,temp);
        }

//        return ResponseStructure.success(data);
        return data;
    }

    @Override
    public String getDictionaryTypeLabelByName(String dictionaryTypeName) {
        LambdaQueryWrapper<DictionaryType> queryWrapper =
                Wrappers.lambdaQuery(DictionaryType.class)
                        .eq(DictionaryType::getTypeName, dictionaryTypeName)
                        .select(DictionaryType::getTypeLabel);
        DictionaryType dictionaryType = this.baseMapper.selectOne(queryWrapper);

        if (dictionaryType == null) {
            return null;
        }
        return dictionaryType.getTypeLabel();
    }

    /**
     * 判断字典类型名称是否存在
     * @param typeName
     * @return
     */
    public Boolean typeNameExist(String typeName){
        QueryWrapper<DictionaryType>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type_name",typeName);
        DictionaryType dictionaryType=baseMapper.selectOne(queryWrapper);
        return dictionaryType!=null;
    }

    /**
     *获取一级字典类型下的二级类型
     * 获取二级类型下面的字典值
     */
    public List<String> getNodeIds(String id)  {
        List<String>resIds=new ArrayList<>();
        resIds.add(id);
        QueryWrapper<DictionaryType>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<DictionaryType>dictionaryTypes=baseMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(dictionaryTypes)){
            for (DictionaryType dictionaryType : dictionaryTypes) {
                delValus(dictionaryType.getId());
                resIds.add(dictionaryType.getId());
            }
        }
        return resIds;
    }
    //删除二级目录下的值
    public void delValus(String id)  {
        List<String>delIds=new ArrayList<>();
        QueryWrapper<DictionaryValue>valueWapper=new QueryWrapper<>();
        valueWapper.eq("type_id",id);
        List<DictionaryValue>dictionaryValues=dictionaryValueMapper.selectList(valueWapper);
        if(!CollectionUtils.isEmpty(dictionaryValues)){
            for (DictionaryValue dictionaryValue : dictionaryValues) {
                delIds.add(dictionaryValue.getId());
            }
        }
        if(!CollectionUtils.isEmpty(delIds)){
            dictionaryValueMapper.deleteBatchIds(delIds);        }

    }

    private DictionaryTypeCatalogVO buildUserTree(List<DictionaryTypeCatalogVO> catalogList, DictionaryTypeCatalogVO catalogParentNode) {
        List<DictionaryTypeCatalogVO> childrenList =new ArrayList<>();
        for (DictionaryTypeCatalogVO catalogHasParentNode : catalogList) {
            // 当前数据的 parentId 等于 父节点的 id，则该数据是当前父级节点的子级。
            if (catalogHasParentNode.getParentId().equals(catalogParentNode.getId())) {
                // 递归调用
                childrenList.add(buildUserTree(catalogList, catalogHasParentNode));
            }
        }
        catalogParentNode.setChildren(childrenList);
        return catalogParentNode;
    }

}
