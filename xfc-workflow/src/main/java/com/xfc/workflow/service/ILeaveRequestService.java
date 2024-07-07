package com.xfc.workflow.service;

import com.xfc.workflow.dto.LeaveRequestDTO;
import com.xfc.workflow.entities.LeaveRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xfc
 * @since 2024-06-30
 */
public interface ILeaveRequestService extends IService<LeaveRequest> {

    Boolean addRequest(LeaveRequestDTO leaveRequestDTO);
}
