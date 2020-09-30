package com.docone.metawrapper.jdbimapper;

import com.docone.metawrapper.model.umls.RXData;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RXDataMapper implements RowMapper<RXData> {
    private static final String CODE = "code";
    private static final String SAB = "sab";
    private static final String STR = "str";
    private static final String RELA = "rela";

    @Override
    public RXData map(ResultSet rs, StatementContext ctx) throws SQLException {
        RXData rxData = new RXData();

        rxData.setCode(rs.getString(CODE));
        rxData.setSab(rs.getString(SAB));
        rxData.setName(rs.getString(STR));
        rxData.setRela(rs.getString(RELA));

        return rxData;
    }
}
