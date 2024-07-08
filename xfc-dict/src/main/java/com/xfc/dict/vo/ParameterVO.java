package com.xfc.dict.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2023-06-01 15:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterVO {
    //后端保存
    private String value;
    //前端展示
    private String label;
}
