package com.xfc.dict.dto;

import com.xfc.common.page.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiao xn
 * @date 2023/7/11 23:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictValuePageQueryRequestDTO extends BaseRequest {
    @ApiModelProperty("字典值标签")
    private String valueName;

    @ApiModelProperty("字典类型ID")
    private String typeId;
}
