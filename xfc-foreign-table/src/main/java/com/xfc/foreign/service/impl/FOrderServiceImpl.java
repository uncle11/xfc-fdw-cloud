package com.xfc.foreign.service.impl;

import com.xfc.foreign.entities.FOrder;
import com.xfc.foreign.mapper.FOrderMapper;
import com.xfc.foreign.service.IFOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenss
 * @since 2023-12-22
 */
@Service
public class FOrderServiceImpl extends ServiceImpl<FOrderMapper, FOrder> implements IFOrderService {
    @Override
    public FOrder getOrderById(Long id) {
        return null;
    }
}
