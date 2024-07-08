package com.xfc.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2023-10-09 17:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
    private long total;
    private List<T> items;


}
