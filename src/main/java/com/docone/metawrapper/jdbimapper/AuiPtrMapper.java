package com.docone.metawrapper.jdbimapper;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuiPtrMapper implements RowMapper<String> {

    private static final String AUI = "AUI";
    private static final String PTR = "PTR";

    @Override
    public String map(ResultSet rs, StatementContext ctx) throws SQLException {
        return rs.getString(PTR) + "." + rs.getString(AUI);
    }
}
