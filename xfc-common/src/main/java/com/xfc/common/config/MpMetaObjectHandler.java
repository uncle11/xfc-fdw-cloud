package com.xfc.common.config;

import com.alibaba.fastjson2.util.DateUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class MpMetaObjectHandler implements MetaObjectHandler {
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.insertFillTimeFiled(metaObject, CREATE_TIME);
        this.insertFillTimeFiled(metaObject, UPDATE_TIME);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.updateFileTimeFiled(metaObject);
    }


    private void insertFillTimeFiled(MetaObject metaObject, String timeFileName) {
        if (metaObject.hasSetter(timeFileName)) {
            if (metaObject.getSetterType(timeFileName).equals(Date.class)) {
                this.strictInsertFill(metaObject, timeFileName, Date.class, currentDate());
            } else if (metaObject.getSetterType(timeFileName).equals(LocalDateTime.class)) {
                this.strictInsertFill(metaObject, timeFileName, LocalDateTime.class, currentLocalDateTime());
            }
        }
    }

    private void updateFileTimeFiled(MetaObject metaObject) {
        if (metaObject.hasSetter(MpMetaObjectHandler.UPDATE_TIME)) {
            if (metaObject.getSetterType(MpMetaObjectHandler.UPDATE_TIME).equals(Date.class)) {
                this.strictUpdateFill(metaObject, MpMetaObjectHandler.UPDATE_TIME,Date.class, currentDate());
            } else if (metaObject.getSetterType(MpMetaObjectHandler.UPDATE_TIME).equals(LocalDateTime.class)) {
                this.strictUpdateFill(metaObject, MpMetaObjectHandler.UPDATE_TIME, LocalDateTime.class, currentLocalDateTime());
            }
        }
    }

    private Date currentDate() {
        return DateUtils.parseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    private LocalDateTime currentLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeFormatter.format(LocalDateTime.now()), dateTimeFormatter);
    }

}