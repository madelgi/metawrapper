package com.docone.metawrapper.jdbimapper;

import com.docone.metawrapper.model.umls.MRConso;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MRConsoMapper implements RowMapper<MRConso> {
    private static final String CUI = "CUI";
    private static final String LAT = "LAT";
    private static final String TS = "TS";
    private static final String LUI = "LUI";
    private static final String STT = "STT";
    private static final String SUI = "SUI";
    private static final String ISPREF = "ISPREF";
    private static final String AUI = "AUI";
    private static final String SAUI = "SAUI";
    private static final String SCUI = "SCUI";
    private static final String SDUI = "SDUI";
    private static final String SAB = "SAB";
    private static final String TTY = "TTY";
    private static final String CODE = "CODE";
    private static final String STR = "STR";
    private static final String SRL = "SRL";
    private static final String SUPPRESS = "SUPPRESS";
    private static final String CVF = "CVF";

    // Optional params
    //private static final String RELA = "RELA";

    @Override
    public MRConso map(ResultSet rs, StatementContext ctx) throws SQLException {
        MRConso mrConso = new MRConso();


        mrConso.setCui(rs.getString(CUI));
        mrConso.setLat(rs.getString(LAT));
        mrConso.setTs(rs.getString(TS));
        mrConso.setLui(rs.getString(LUI));
        mrConso.setStt(rs.getString(STT));
        mrConso.setSui(rs.getString(SUI));
        mrConso.setIsPref(rs.getString(ISPREF));
        mrConso.setAui(rs.getString(AUI));
        mrConso.setSaui(rs.getString(SAUI));
        mrConso.setScui(rs.getString(SCUI));
        mrConso.setSdui(rs.getString(SDUI));
        mrConso.setSab(rs.getString(SAB));
        mrConso.setTty(rs.getString(TTY));
        mrConso.setCode(rs.getString(CODE));
        mrConso.setStr(rs.getString(STR));
        mrConso.setSrl(rs.getString(SRL));
        mrConso.setSuppress(rs.getString(SUPPRESS));
        mrConso.setCvf(rs.getString(CVF));

        //if (!rs.getString(RELA).isEmpty()) {
        //    mrConso.setRela(rs.getString(RELA));
        //}

        return mrConso;
    }
}
