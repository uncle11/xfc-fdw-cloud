package com.xfc.foreign.service.impl;

import com.xfc.foreign.entities.FUser;
import com.xfc.foreign.mapper.FUserMapper;
import com.xfc.foreign.service.IFUserService;
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
public class FUserServiceImpl extends ServiceImpl<FUserMapper, FUser> implements IFUserService {

}
