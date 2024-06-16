package com.xfc.workflow.service;

import com.xfc.workflow.entities.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author xfc
 * @since 2024-06-16
 */
public interface ISysUserService extends IService<SysUser> {
    SysUser findByUsername(String username);
}
