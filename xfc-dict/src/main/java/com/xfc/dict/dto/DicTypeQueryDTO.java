package com.xfc.dict.dto;


import com.xfc.dict.entities.DictionaryType;
import com.xfc.common.page.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: chenss
 * @date 2023-05-18 15:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DicTypeQueryDTO extends BaseRequest<DictionaryType> {
    private String typeName;
    private String parentId;

    public DicTypeQueryDTO(long current, long size, String typeName, String parentId) {
        super(current, size);
        this.typeName = typeName;
        this.parentId = parentId;
    }
}
