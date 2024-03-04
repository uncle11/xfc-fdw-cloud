package com.xfc.ltree.entities;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xfc.ltree.config.LTreeTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenss
 * @since 2024-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LtreeCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    @TableField(value = "path", typeHandler = LTreeTypeHandler.class)
    private String path;

    private Boolean isDeleted;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    private Integer version;


}
