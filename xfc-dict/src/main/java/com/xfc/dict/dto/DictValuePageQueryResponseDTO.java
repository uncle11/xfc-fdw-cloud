package com.xfc.dict.dto;

import lombok.Data;

/**
 * @author jiao xn
 * @date 2023/7/11 23:45
 */
@Data
public class DictValuePageQueryResponseDTO {
    private String id;
    private String typeName;
    private String valueName;
    private String valueLabel;
    private Integer valueSort;
    private String createTime;
}
