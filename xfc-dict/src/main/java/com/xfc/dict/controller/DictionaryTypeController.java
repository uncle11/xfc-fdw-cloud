package com.xfc.dict.controller;


import com.xfc.dict.dto.DicTypeDTO;
import com.xfc.dict.dto.DicTypeQueryDTO;
import com.xfc.dict.entities.DictionaryType;
import com.xfc.dict.service.IDictionaryTypeService;
import com.xfc.dict.service.IDictionaryValueService;
import com.xfc.dict.vo.DictionaryTypeCatalogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@Api("字典类型管理")
@RestController
@RequestMapping("/dictionary-type")
@Slf4j
public class DictionaryTypeController {
    @Autowired
    private IDictionaryTypeService dictionaryTypeService;
    @Autowired
    private IDictionaryValueService iDictionaryValueService;
    /**
     * 获取一级字典类型列表
     * @return
     */
    @ApiOperation("获取一级字典类型列表")
    @GetMapping("/one-level/list")
    public List<DictionaryType> getOneLevelList(){

        return dictionaryTypeService.getOneLevelList();
    }
    /**
     * 新增字典类型数据
     */
    @ApiOperation("新增字典类型数据")
    @PostMapping("/all-level")
    public String addType(@RequestBody DicTypeDTO dicTypeDTO){
        return dictionaryTypeService.addType(dicTypeDTO);
    }
    /**
     * 修改字典类型数据
     */
    @ApiOperation("修改字典类型数据")
    @PutMapping("/all-level/{id}")
    public String updateType(@PathVariable("id") String id,@RequestBody DicTypeDTO dicTypeDTO){
        return dictionaryTypeService.updateType(id,dicTypeDTO);
    }
    /**
     * 删除字典类型数据
     */
    @ApiOperation("删除字典类型数据")
    @DeleteMapping("/all-level/{id}")
    public String deleteType(@PathVariable("id")String id){
        try {
            return dictionaryTypeService.deleteType(id);
        } catch (Exception e) {
            log.error("删除字典类型出错"+e.getMessage(),e);
            return "删除字典类型出错";
        }
    }
    /**
     * 根据条件分页查询二级字典类型数据
     */
    @ApiOperation("根据条件分页查询二级字典类型数据")
    @GetMapping("/two-level/search")
    public Map<String, Object> getTwoLevelPage(@RequestParam long page, @RequestParam long pageSize,
                                               @RequestParam(required = false) String typeName, @RequestParam(required = false) String parentId){
        DicTypeQueryDTO dicTypeQueryDTO=new DicTypeQueryDTO(page,pageSize,typeName,parentId);
        return dictionaryTypeService.getTwoLevelPage(dicTypeQueryDTO);
    }
    /**
     * 查看二级字典类型数据详情
     */
    @ApiOperation("查看二级字典类型数据详情")
    @GetMapping("two-level/{id}")
    public DictionaryType getTwoLevelData(@PathVariable("id")String id){
        return dictionaryTypeService.getTwoLevelData(id);
    }

    /**
     * 查看数据描述
     */
    @ApiOperation("查看数据描述")
    @GetMapping("{id}")
    public String getDescriptionById(@PathVariable("id")String id){
        return dictionaryTypeService.getDescriptionById(id);
    }

    @ApiOperation("获取字典类型目录树")
    @GetMapping("catalog")
    public List<DictionaryTypeCatalogVO> getCatalog(){
        return dictionaryTypeService.getCatalog();
    }
    /**
     * 获取模块数据字典
     * @param typeName
     * @return
     */
    @ApiOperation("获取某个模块下的数据字典")
    @GetMapping("/parameter/{typeName}")
    Map<String, Object> getCompleteParameter(@PathVariable("typeName") String typeName){
        return iDictionaryValueService.getParameters(typeName);
    }
}
