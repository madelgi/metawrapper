package com.docone.metawrapper.dao;

import com.docone.metawrapper.jdbimapper.*;
import com.docone.metawrapper.model.TreeNode;
import com.docone.metawrapper.model.umls.MRConso;
import com.docone.metawrapper.model.umls.RXData;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.AbstractMap;
import java.util.List;

//@LogSqlFactory
public interface MRConsoDao {
    /**
     * RX related queries.
     */
    @SqlQuery("SELECT AUI2, RELA FROM MRREL WHERE CUI1 = :cui and RELA IN (\"may_treat\",\"may_prevent\")")
    @UseRowMapper(SimpleEntryStringStringMapper.class)
    List<AbstractMap.SimpleEntry<String, String>> getRxFromCui(@Bind("cui") String cui);

    @SqlQuery("SELECT DISTINCT AUI2, RELA FROM MRREL " +
              "WHERE CUI1 in (<cuis>) and RELA IN (\"may_treat\",\"may_prevent\") AND SAB IN (\"RXNORM\",\"MED-RT\")")
    @UseRowMapper(SimpleEntryStringStringMapper.class)
    List<AbstractMap.SimpleEntry<String, String>> getRxFromCuis(@BindList("cuis") List<String> cuis);

    @SqlQuery("SELECT DISTINCT CODE " +
              "FROM MRCONSO " +
              "WHERE CUI = :cui AND SAB = \"MSH\";")
    String getMeshFromCui(@Bind("cui") String cui);

    // TODO potential speed up by adding restricting on drug source as IN,MIN, etc
    @SqlQuery(
            "SELECT DISTINCT d.CODE, d.STR, d.SAB, r.RELA " +
            "FROM MRREL as r " +
                    "JOIN MRCONSO as c ON c.CUI = r.CUI1 " +
                    "JOIN MRCONSO as d on d.CUI = r.CUI2 " +
            "WHERE c.CODE in (<codes>) AND c.TTY = \"MH\" " +
                    "AND r.RELA IN (\"may_treat\",\"may_prevent\") " +
                    "AND d.SAB = \"RXNORM\" AND d.TTY = \"IN\";")
    @UseRowMapper(RXDataMapper.class)
    List<RXData> getRxFromCodes(@BindList("codes") List<String> codes);

    @SqlQuery("SELECT DISTINCT AUI2, RELA " +
              "FROM MRREL " +
              "WHERE CUI1 IN (" +
                    "SELECT DISTINCT CUI2 " +
                    "FROM MRCONSO JOIN MRREL ON MRCONSO.CUI=MRREL.CUI1 " +
                    "WHERE MRCONSO.STR = :str AND MRCONSO.SAB=\"MSH\" AND MRREL.REL IN (\"RL\",\"RO\",\"SY\",\"RQ\",\"RU\")" +
              ") AND RELA IN (\"may_treat\",\"may_prevent\") AND SAB IN (\"RXNORM\",\"MED-RT\");")
    @UseRowMapper(SimpleEntryStringStringMapper.class)
    List<AbstractMap.SimpleEntry<String, String>> getRxFromString(@Bind("str") String str);

    /**
     * MeSH related queries.
     */
    @SqlQuery("SELECT DISTINCT MRCONSO.CODE " +
            "FROM MRCONSO JOIN MRREL ON MRCONSO.CUI=MRREL.CUI1 " +
            "WHERE MRCONSO.STR = :str AND MRCONSO.SAB=\"MSH\" AND MRREL.REL=\"RO\";")
    List<String> getMeshCodesFromString(@Bind("str") String str);

    @SqlQuery("SELECT CODE " +
              "FROM MRCONSO " +
              "WHERE STR = :str AND SAB = \"MSH\" AND TTY = \"MH\";")
    String getMeshCodeFromString(@Bind("str") String str);

    @SqlQuery("SELECT DISTINCT m.STR, m.CODE, h.HCD " +
              "FROM MRHIER as h JOIN MRCONSO as m ON h.AUI = m.AUI " +
              "WHERE h.SAB=\"MSH\" AND h.HCD = :hcd;")
    @UseRowMapper(MeshTreeNodeMapper.class)
    List<TreeNode> getMeshFromHcd(@Bind("hcd") String hcd);

    @SqlQuery("SELECT DISTINCT m.STR, m.CODE, h.HCD " +
              "FROM MRHIER as h JOIN MRCONSO as m ON h.AUI = m.AUI " +
              "WHERE h.SAB=\"MSH\" AND h.HCD in (<hcds>);")
    @UseRowMapper(MeshTreeNodeMapper.class)
    List<TreeNode> getMeshFromHcds(@BindList("hcds") List<String> hcds);

    @SqlQuery("SELECT DISTINCT m.STR, m.CODE, h.HCD " +
              "FROM MRHIER as h JOIN MRCONSO as m ON h.AUI = m.AUI " +
              "WHERE h.SAB=\"MSH\" AND h.HCD LIKE CONCAT(:hcd, '.___');")
    @UseRowMapper(MeshTreeNodeMapper.class)
    List<TreeNode> getMeshChildrenFromHcd(@Bind("hcd") String hcd);

    @SqlQuery("SELECT DISTINCT m.STR, m.CODE, h.HCD " +
              "FROM MRHIER as h JOIN MRCONSO as m ON h.AUI = m.AUI " +
              "WHERE h.SAB=\"MSH\" AND h.HCD LIKE CONCAT(:root, '__');")
    @UseRowMapper(MeshTreeNodeMapper.class)
    List<TreeNode> getMeshRootChildren(@Bind("root") String root);

    @SqlQuery("SELECT DISTINCT m.STR, m.CODE, h.PTR, h.AUI " +
              "FROM MRHIER as h JOIN MRCONSO as m ON h.AUI = m.AUI " +
              "WHERE m.SAB=\"CPT\" AND m.CODE IN (<roots>);")
    @UseRowMapper(CPTTreeNodeMapper.class)
    List<TreeNode> getCptNodesFromCodes(@BindList("roots") List<String> roots);

    @SqlQuery("SELECT DISTINCT m.STR, m.CODE, h.PTR, h.AUI " +
              "FROM MRHIER as h JOIN MRCONSO as m ON h.AUI = m.AUI " +
              "WHERE h.SAB=\"CPT\" AND h.PTR = :ptr;")
    @UseRowMapper(CPTTreeNodeMapper.class)
    List<TreeNode> getCptChildrenFromHierarchy(@Bind("ptr") String ptr);

    @SqlQuery("SELECT DISTINCT HCD " +
              "FROM MRHIER as h JOIN MRCONSO as c ON h.AUI = c.AUI " +
              "WHERE h.SAB = \"MSH\" AND CODE = :code " +
              "AND (h.HCD like 'C%' OR h.HCD like 'F%');")
    List<String> getHcdsFromCode(@Bind("code") String code);

    @SqlQuery("SELECT DISTINCT h.PTR, h.AUI " +
              "FROM MRHIER as h JOIN MRCONSO as c ON h.AUI = c.AUI " +
              "WHERE h.SAB = \"CPT\" AND CODE = :code;")
    @UseRowMapper(AuiPtrMapper.class)
    List<String> getHierarchyFromCode(@Bind("code") String code);

    @SqlQuery("SELECT DISTINCT crx.STR, cmesh.STR, r.RELA, r.SAB " +
              "FROM MRREL as r " +
              "    JOIN MRCONSO as crx ON r.AUI1 = crx.AUI " +
              "    JOIN MRCONSO as cmesh ON r.AUI2 = cmesh.AUI " +
              "WHERE crx.SAB = \"RXNORM\" AND cmesh.SAB = \"MSH\" AND crx.CODE = :code " +
              "  AND r.RELA IN (\"may_be_treated_by\", \"may_be_prevented_by\");")
    List<TreeNode> getMeshFromRxCode(@Bind("code") String code);

    @SqlQuery("SELECT DISTINCT CODE " +
              "FROM MRCONSO " +
              "WHERE SAB = \"RXNORM\" AND STR = :str")
    String getRxCodeFromString(@Bind("str") String str);

    /**
     * General UMLS queries.
     */
    @SqlQuery("SELECT DISTINCT CUI2 " +
              "FROM MRCONSO JOIN MRREL ON MRCONSO.CUI=MRREL.CUI1 " +
              "WHERE MRCONSO.STR = :str AND MRCONSO.SAB=\"MSH\" AND MRREL.REL=\"RO\";")
    List<String> getRelatedCuisFromString(@Bind("str") String s);

    @SqlQuery("SELECT * FROM MRCONSO WHERE AUI IN (<auis>);")
    @UseRowMapper(MRConsoMapper.class)
    List<MRConso> getMRConsoFromAuis(@BindList("auis") List<String> auis);
}
