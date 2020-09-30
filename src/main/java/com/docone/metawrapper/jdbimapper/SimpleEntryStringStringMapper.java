package com.docone.metawrapper.jdbimapper;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;

public class SimpleEntryStringStringMapper implements RowMapper<AbstractMap.SimpleEntry<String, String>> {

    @Override
    @SuppressWarnings("unchecked")
    public AbstractMap.SimpleEntry<String, String> map(ResultSet rs, StatementContext ctx) throws SQLException {
        String col1 = rs.getMetaData().getColumnName(1);
        String col2 = rs.getMetaData().getColumnName(2);
        return new AbstractMap.SimpleEntry<>((String)rs.getObject(col1), (String)rs.getObject(col2));
    }
}
