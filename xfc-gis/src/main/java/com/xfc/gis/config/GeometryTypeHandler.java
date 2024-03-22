package com.xfc.gis.config;


import net.postgis.jdbc.PGgeometry;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Postgis Geometry 类型处理类
 *
 * @author jiao xn
 * @date 2022/9/12
 */
@MappedTypes({String.class})
public class GeometryTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(s);
        preparedStatement.setObject(i, pGgeometry, jdbcType.TYPE_CODE);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(resultSet.getString(s));
        return pGgeometry.toString();
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(resultSet.getString(i));
        return pGgeometry.toString();
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(callableStatement.getString(i));
        return pGgeometry.toString();
    }
}
