package com.apwglobal.allegro.server.db;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;

import java.sql.*;
import java.util.Optional;

public class OptionalBasicTypesHandler extends TypeReference<Optional> implements TypeHandler<Optional<?>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Optional parameter, JdbcType jdbcType) throws SQLException {

        if (jdbcType == null) {
            throw new IllegalArgumentException("Cannot set value for Optional field without knowing jdbcType");
        }

        if (parameter.isPresent()) {
            ps.setObject(i, parameter.get(), jdbcType.TYPE_CODE);
        } else {
            ps.setNull(i, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public Optional getResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getObject(columnName));
    }

    @Override
    public Optional getResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getObject(columnIndex));
    }

    @Override
    public Optional getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Optional.ofNullable(cs.getObject(columnIndex));
    }
}
