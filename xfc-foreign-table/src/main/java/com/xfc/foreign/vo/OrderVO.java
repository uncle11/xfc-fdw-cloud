package com.xfc.foreign.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2023-12-22 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private String userName;
    private String name;
    private Long price;
    private Integer num;
}
