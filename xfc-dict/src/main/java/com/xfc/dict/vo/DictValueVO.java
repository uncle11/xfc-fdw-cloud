package com.xfc.dict.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: 李帅兵
 * @description 字典值的分页VO
 * @date: 2023/5/19 13:48
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictValueVO {
    //字典值
   private String id;
   private String valueName;
   private String typeName;
   private String typeDescription;
   private String creator;
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
   private Date createTime;
   private String valueLabel;
   private int valueSort;

}
