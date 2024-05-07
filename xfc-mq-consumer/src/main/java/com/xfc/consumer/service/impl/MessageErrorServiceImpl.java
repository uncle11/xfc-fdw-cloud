package com.xfc.consumer.service.impl;

import com.xfc.consumer.entities.MessageError;
import com.xfc.consumer.mapper.MessageErrorMapper;
import com.xfc.consumer.service.IMessageErrorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-05-04
 */
@Service
public class MessageErrorServiceImpl extends ServiceImpl<MessageErrorMapper, MessageError> implements IMessageErrorService {

}
