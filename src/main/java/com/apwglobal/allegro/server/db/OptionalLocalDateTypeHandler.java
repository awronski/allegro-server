package com.apwglobal.allegro.server.db;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

public class OptionalLocalDateTypeHandler extends BaseTypeHandler<Optional<LocalDateTime>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Optional<LocalDateTime> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter.isPresent()) {
            Date d = Date.from(parameter.get().atZone(ZoneOffset.systemDefault()).toInstant());
            ps.setTimestamp(i, new Timestamp(d.getTime()));
        } else {
            ps.setTimestamp(i, null);
        }
    }

    @Override
    public Optional<LocalDateTime> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnName);
        return getLocalDateTime(sqlTimestamp);
    }

    @Override
    public Optional<LocalDateTime> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
        return getLocalDateTime(sqlTimestamp);
    }

    @Override
    public Optional<LocalDateTime> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
        return getLocalDateTime(sqlTimestamp);
    }

    private Optional<LocalDateTime> getLocalDateTime(Timestamp sqlTimestamp) {
        if (sqlTimestamp != null) {
            Date d = new Date(sqlTimestamp.getTime());
            return Optional.of(LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault()));
        }
        return Optional.empty();
    }

}
