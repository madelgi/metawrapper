package com.docone.metawrapper.jdbimapper;

import com.docone.metawrapper.model.TreeNode;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeshTreeNodeMapper implements RowMapper<TreeNode> {
    private static final String HCD = "HCD";
    private static final String STR = "STR";
    private static final String CODE = "CODE";

    @Override
    public TreeNode map(ResultSet rs, StatementContext ctx) throws SQLException {
        TreeNode treeNode = new TreeNode();

        treeNode.setHierarchy(rs.getString(HCD));
        treeNode.setName(rs.getString(STR));
        treeNode.setCode(rs.getString(CODE));

        return treeNode;
    }
}
