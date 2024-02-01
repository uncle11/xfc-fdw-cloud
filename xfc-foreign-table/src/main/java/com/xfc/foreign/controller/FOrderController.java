package com.xfc.foreign.controller;


import com.xfc.foreign.entities.FOrder;
import com.xfc.foreign.entities.FUser;
import com.xfc.foreign.service.IFOrderService;
import com.xfc.foreign.service.IFUserService;
import com.xfc.foreign.utils.BeanCopyUtils;
import com.xfc.foreign.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenss
 * @since 2023-12-22
 */
@RestController
@RequestMapping("/f-order")
public class FOrderController {
    @Autowired
    IFOrderService orderService;
    @Autowired
    IFUserService userService;
    @GetMapping("/{id}")
    public OrderVO getOrderById(@PathVariable("id")Long id){
        FOrder order = orderService.getById(id);
        FUser userByOrderId = userService.getById(order.getUserId());
        OrderVO orderVO= BeanCopyUtils.copyBean(order,OrderVO.class);
        orderVO.setUserName(userByOrderId.getUsername());
        return orderVO;
    }

}
