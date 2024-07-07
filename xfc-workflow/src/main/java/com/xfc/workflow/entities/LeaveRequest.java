package com.xfc.workflow.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.xfc.common.entities.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xfc
 * @since 2024-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LeaveRequest对象", description="")
public class LeaveRequest extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "业务流程id")
    private String workflowId;

    @ApiModelProperty(value = "请假理由")
    private String purpose;

    @ApiModelProperty(value = "请假天数")
    private Integer leaveDays;

    @ApiModelProperty(value = "申请人姓名")
    private String applicantName;

    @ApiModelProperty(value = "申请人用户名")
    private String applicatUsername;

    @ApiModelProperty(value = "申请人电话")
    private String applicantPhone;



}
