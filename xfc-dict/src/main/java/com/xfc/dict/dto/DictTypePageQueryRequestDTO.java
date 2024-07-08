package com.xfc.dict.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictTypePageQueryRequestDTO {
    private String id;
    private String typeName;
    private String typeDescription;
    private String parentId;
    private String typeLabel;
    private String creatorId;
    private String creatorName;
    private String updaterId;
    private String updaterName;
    private String createTime;
    private String updateTime;
    private Boolean isVisible;
    private Boolean isDeleted;
    private Integer version;
}
