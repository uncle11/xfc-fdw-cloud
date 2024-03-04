package com.xfc.ltree.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @version 1.0
 * @Author zhaozhixin
 * @Date 2023/8/24 14:52
 * @注释
 */
@MappedTypes(String.class)
public class LTreeTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("ltree");
        pgObject.setValue(s);
        preparedStatement.setObject(i, pgObject);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i);
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i);
    }
}
