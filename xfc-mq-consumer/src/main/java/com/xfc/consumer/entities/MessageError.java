package com.xfc.consumer.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xfc
 * @since 2024-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MessageError对象", description="")
@NoArgsConstructor
@AllArgsConstructor
public class MessageError implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String messageId;

    private String errorLog;

    private Date createTime;

    private Date updateTime;

    public MessageError(String messageId, String errorLog, Date createTime) {
        this.messageId = messageId;
        this.errorLog = errorLog;
        this.createTime = createTime;
    }
}
