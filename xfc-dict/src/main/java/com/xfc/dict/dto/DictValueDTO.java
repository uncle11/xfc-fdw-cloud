package com.xfc.dict.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典值传输对象
 *
 * @author jiao xn
 * @date 2023/6/5 22:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictValueDTO {
    // 字典标签
    private String label;

    // 字典值
    private String value;
}
