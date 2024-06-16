package com.xfc.workflow.service.impl;

import com.xfc.workflow.entities.Request;
import com.xfc.workflow.mapper.RequestMapper;
import com.xfc.workflow.service.IRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 请求表 服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
@Service
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements IRequestService {

}
