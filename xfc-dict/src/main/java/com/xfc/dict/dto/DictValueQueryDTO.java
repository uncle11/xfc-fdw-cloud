package com.xfc.dict.dto;


import com.xfc.common.page.BaseRequest;
import com.xfc.dict.vo.DictValueVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: 李帅兵
 * @date: 2023/5/19 10:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictValueQueryDTO extends BaseRequest<DictValueVO> {
    private String valueName;
    private String typeId;

    public DictValueQueryDTO(long current, long size, String valueName,String typeId) {
        super(current, size);
        this.valueName = valueName;
        this.typeId=typeId;
    }
}
