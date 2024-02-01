package com.xfc.foreign.service;

import com.xfc.foreign.entities.FOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenss
 * @since 2023-12-22
 */
public interface IFOrderService extends IService<FOrder> {

    FOrder getOrderById(Long id);
}
