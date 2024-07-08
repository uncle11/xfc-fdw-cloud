package com.xfc.dict.controller;


import com.xfc.common.vo.PageVO;
import com.xfc.dict.dto.DictValuePageQueryRequestDTO;
import com.xfc.dict.dto.DictValuePageQueryResponseDTO;
import com.xfc.dict.dto.DictValueSavingDTO;
import com.xfc.dict.service.IDictionaryValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典值表 前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@Api("字典值管理")
@RestController
@RequestMapping("/dictionary-value")
public class DictionaryValueController {
    private final IDictionaryValueService dictionaryValueService;
    public DictionaryValueController(IDictionaryValueService dictionaryValueService) {
        this.dictionaryValueService = dictionaryValueService;
    }

    @ApiOperation("字典值分页查询")
    @GetMapping("/search")
    public PageVO<DictValuePageQueryResponseDTO> getDictValuePage(
            DictValuePageQueryRequestDTO dictValuePageQueryRequestDTO){
        return dictionaryValueService.getDictValuePage(dictValuePageQueryRequestDTO);
    }

    @ApiOperation("新增字典值")
    @PostMapping("")
    public String addValue(@Validated @RequestBody DictValueSavingDTO dictValueSavingDTO){
        dictionaryValueService.saveDictValue(dictValueSavingDTO);

        return "录入成功";
    }

    @ApiOperation("更新字典值")
    @PutMapping("/{id}")
    public String updateValue(@PathVariable("id") String id,
                              @Validated @RequestBody DictValueSavingDTO dictValueSavingDTO){
        dictValueSavingDTO.setId(id);
        dictionaryValueService.saveDictValue(dictValueSavingDTO);

        return "更新成功";
    }

    @ApiOperation("删除字典值")
    @DeleteMapping("/{id}")
    public String deleteValue(@PathVariable("id") String id){
        dictionaryValueService.deleteValue(id);

        return "删除成功";
    }
}
