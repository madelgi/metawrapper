package com.docone.metawrapper.service;

import com.docone.metawrapper.model.TreeNode;
import org.junit.Test;

import java.util.List;

public class MeshTreeServiceIntegrationTest {

    private static final String NEOPLASMS = "C04";
    private static final String EYE_DISEASES = "C11";

    @Test
    public void testGetChildren() {
        final MeshTreeService mts = new MeshTreeService(null);

        List<TreeNode> treeNodes = mts.getChildren(NEOPLASMS);
        assert !treeNodes.isEmpty();

        for (TreeNode t : treeNodes) {
            assert t.getHierarchy().substring(0, 3).equals(NEOPLASMS);
        }
    }


}
