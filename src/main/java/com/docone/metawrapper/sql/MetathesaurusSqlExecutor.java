package com.docone.metawrapper.sql;

import com.docone.metawrapper.model.cpt.CPTTree;

import javax.print.DocFlavor;
import java.util.AbstractMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;

public class MetathesaurusSqlExecutor {

    private static final String MAY_TREAT = "may_treat";
    private static final String MAY_PREVENT = "may_prevent";

    private static final Logger LOGGER = Logger.getLogger(MetathesaurusSqlExecutor.class.getName());

    public MetathesaurusSqlExecutor() {}

    public Map<String, List<String>> getMeshFromCui(String cui) throws SQLException {
        Map<String, List<String>> termMap = new HashMap<>();
        String queryStr = "SELECT CODE, STR FROM MRCONSO WHERE CUI = \"" + cui + "\" and SAB = \"MSH\"";

        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(queryStr)) {
            while (rs.next()) {
                String code = rs.getString("CODE");
                String term = rs.getString("STR");
                if (termMap.containsKey(code)) {
                    termMap.get(code).add(term);
                } else {
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(term);
                    termMap.put(code, arrayList);
                }
            }
        }

        return termMap;
    }

    public List<AbstractMap.SimpleEntry<String, String>> getMeshFromCuis(List<String> cuis) throws SQLException {
        List<AbstractMap.SimpleEntry<String, String>> meshList = new ArrayList<>();
        String queryStr = "SELECT CODE, STR FROM MRCONSO WHERE (CUI in " + collectionToString(cuis) + " and SAB = \"MSH\")";

        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(queryStr)) {
            while (rs.next()) {
                AbstractMap.SimpleEntry<String, String> meshPair = new AbstractMap.SimpleEntry<>(rs.getString("CODE"), rs.getString("STR"));
                meshList.add(meshPair);
            }
        }

        return meshList;
    }

    public Set<String> getAuisFromCuisAndSource(Collection<String> cuis, String source) throws SQLException {
        Set<String> auis = new HashSet<>();
        String query = "SELECT AUI FROM MRCONSO WHERE CUI IN " + collectionToString(cuis) + "AND SAB = \"" + source + "\";";
        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                auis.add(rs.getString("AUI"));
            }
        }
        return auis;
    }

    public Set<String> getChildAuis(String parent) throws SQLException {
        Set<String> cuis = new HashSet<>();
        String query = "SELECT AUI2 FROM MRREL INNER JOIN MRCONSO ON MRREL.AUI2=MRCONSO.AUI WHERE AUI1 = \"" + parent + "\" AND RELA = \"isa\" AND MRREL.SAB = \"CPT\";";

        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String cui = rs.getString("AUI2");
                cuis.add(cui);
            }
        }

        return cuis;
    }

    public String getCodeFromAui(String aui, String source) throws SQLException {
        String query = "SELECT CODE FROM MRCONSO WHERE AUI = \"" + aui + "\" AND SAB = \"" + source + "\" AND (ISPREF = \"N\" OR STT = \"PF\");";
        String code = null;

        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                code = rs.getString("CODE");
            }
        }

        return code;
    }

    public Set<String> getCodesFromAuis(Collection<String> auis, String source) throws SQLException {
        String query = "SELECT CODE FROM MRCONSO WHERE AUI in " + collectionToString(auis) + " AND SAB = \"" + source + "\";";
        Set<String> codes = new HashSet<>();

        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                codes.add(rs.getString("CODE"));
            }
        }

        return codes;
    }


    public String getConditionAui(String code, String source, String type) throws SQLException {
        String query = "SELECT AUI FROM MRCONSO WHERE " + type + " = \"" + code + "\" AND SAB = \"" + source + "\"";  // AND TTY = \"MH\";";
        LOGGER.info(query);
        String aui = null;

        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                aui = rs.getString("AUI");
            }
        }
        return aui;
    }

    public Map<String, List<String>> getRxFromAui(String aui) throws SQLException {
        HashMap<String, List<String>> drugMap = new HashMap<>();
        drugMap.put("mayTreat", new ArrayList<>());
        drugMap.put("mayPrevent", new ArrayList<>());

        String query = "SELECT AUI2, RELA FROM MRREL WHERE AUI1 = \"" + aui + "\" AND RELA IN (\"" + MAY_TREAT + "\",\"" + MAY_PREVENT + "\");";

        try ( Connection conn = ConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String aui2 = rs.getString("AUI2");
                String relType = rs.getString("RELA");
                if (relType.equals(MAY_TREAT)) {
                    drugMap.get("mayTreat").add(aui2);
                } else {
                    drugMap.get("mayPrevent").add(aui2);
                }
            }
        }

        return drugMap;
    }

    /**
     * Utility methods.
     */
    public boolean ping() throws SQLException {
        String queryStr = "SELECT 1";
        LOGGER.info(queryStr);
        Statement stmt = ConnectionManager.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(queryStr);

        return rs.next();
    }

    private static String collectionToString(Collection<String> collection) {
        if (collection.isEmpty()) {
            return "()";
        }

        StringBuilder finalString = new StringBuilder();
        finalString.append("(");
        for (String item : collection) {
            finalString.append("\"");
            finalString.append(item);
            finalString.append("\"");
            finalString.append(",");
        }

        finalString.deleteCharAt(finalString.length() - 1);
        finalString.append(")");
        return finalString.toString();
    }

}
