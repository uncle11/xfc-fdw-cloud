package com.xfc.dict.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xfc.dict.anno.Dict;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-07-07 15:59
 */
@Data
public class StudentVO {
    private String id;

    private String name;

    @Dict(type = "bloodType")
    private String bloodType;

    @Dict(type = "constellationType")
    private String constellationType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
