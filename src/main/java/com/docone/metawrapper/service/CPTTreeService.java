package com.docone.metawrapper.service;

import com.docone.metawrapper.dao.MRConsoDao;
import com.docone.metawrapper.model.TreeNode;
import com.docone.metawrapper.util.FlatTree;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class CPTTreeService {
    private static final Logger LOGGER = Logger.getLogger(CPTTreeService.class.getName());
    private static final String FPATH = System.getProperty("user.home") + "/.metawrapper/data/";
    private static final String FNAME = "cpt_flat_tree.cer";
    private static final String ROOT_NAME = "ROOT";
    private static final List<String> ROOT_CODES = Arrays.asList(
            "1022290", "1002796", "1014507", "1014405", "1013625", "1028554", "1014311", "1012569",
            "1021435", "1011136", "1021863", "1010251", "1003143"
    );

    private MRConsoDao mrConsoDao;
    private FlatTree<List<TreeNode>> flatTree;

    /**
     * Construct a FlatTree of CPT codes using SQL queries.
     */
    public CPTTreeService(MRConsoDao mrConsoDao) {
        this.mrConsoDao = mrConsoDao;

        LOGGER.info("Constructing CPTTreeService");
        File dir = new File(FPATH);
        if (!dir.exists()) {
            LOGGER.info("Creating .metawrapper directory");
            if (!dir.mkdirs()) {
                LOGGER.warning("Failed to create .metawrapper directory");
            }
        }

        System.out.println(FPATH + FNAME);
        File f = new File(FPATH + FNAME);
        if (f.exists()) {
            LOGGER.info("Loading CPTTreeService from cache.");
            try (InputStream file = new FileInputStream(FPATH + FNAME);
                 InputStream buffer = new BufferedInputStream(file);
                 ObjectInput input = new ObjectInputStream(buffer)
                ) {
                this.flatTree = (FlatTree) input.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex);
                this.flatTree = buildFlatTree();
            }

        } else {
            LOGGER.info("Building/saving CPTTreeService.");
            this.flatTree = buildFlatTree();
            try (OutputStream file = new FileOutputStream(FPATH + FNAME);
                 OutputStream buffer = new BufferedOutputStream(file);
                 ObjectOutput output = new ObjectOutputStream(buffer)
                ) {
                output.writeObject(this.flatTree);
            } catch (IOException ex) {
                // DO nothing
                LOGGER.warning("Unable to serialize CPTTreeService");
            }
        }
    }

    public List<TreeNode> getAllChildrenFromCode(String code) {
        List<String> ptrs = mrConsoDao.getHierarchyFromCode(code);
        List<TreeNode> children = new ArrayList<>();
        for (String ptr : ptrs) {
            children.addAll(getAllChildren(ptr));
        }

        return children;
    }

    public List<TreeNode> getAllChildren(List<String> hcds) {
        List<TreeNode> children = new ArrayList<>();
        for (String hcd : hcds) {
            children.addAll(getAllChildren(hcd));
        }

        return children;
    }

    /**
     * TODO should probably be abstracted and handled by FlatTree class.
     * @param hcd
     * @return A List of MeshTreeNodes.
     */
    public List<TreeNode> getAllChildren(String hcd) {
        List<TreeNode> allChildren = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        allChildren.addAll(flatTree.getChildren(hcd));
        stack.addAll(flatTree.getChildren(hcd));
        while (!stack.isEmpty()) {
            TreeNode currentNode = stack.pop();
            allChildren.addAll(flatTree.getChildren(currentNode.getHierarchy()));
            stack.addAll(flatTree.getChildren(currentNode.getHierarchy()));
        }

        return allChildren;
    }

    /**
     * Builds the FlatTree structure for MeSH terms.
     *
     * @return A FlatTree.
     */
    private FlatTree<List<TreeNode>> buildFlatTree() {
        Map<String, List<TreeNode>> childMap = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        Map<String, Boolean> seen = new HashMap<>();

        Stack<TreeNode> stack = new Stack<>();

        // Initialize maps, stack
        List<TreeNode> initialCpt = new ArrayList<>();
        initialCpt.addAll(mrConsoDao.getCptNodesFromCodes(ROOT_CODES));
        childMap.put(ROOT_NAME, initialCpt);

        for (TreeNode mesh : initialCpt) {
            parentMap.put(mesh.getHierarchy(), ROOT_NAME);
            stack.add(mesh);
        }

        int processedNodes = 0;
        while (!stack.isEmpty()) {
            TreeNode currentNode = stack.pop();

            // TODO just for progress, can probably remove after development
            if (processedNodes % 500 == 0) {
                LOGGER.info("Processed " + processedNodes + " CPT codes.");
            }

            // NOTE: I think this should only happen if the tree has a loop, but I don't think that will ever happen
            if (seen.containsKey(currentNode.getHierarchy())) {
                continue;
            } else {
                seen.put(currentNode.getHierarchy(), true);
            }

            List<TreeNode> children = mrConsoDao.getCptChildrenFromHierarchy(currentNode.getHierarchy());
            if (!children.isEmpty()) {
                for (TreeNode childMesh : children) {
                    parentMap.put(childMesh.getHierarchy(), currentNode.getHierarchy());
                }
                stack.addAll(children);
            }

            // Multiple hierarchical structures --
            if (childMap.containsKey(currentNode.getHierarchy())) {
                LOGGER.info("THIS HAPPENS: " + currentNode.getHierarchy());
                List<String> currentCodes = new ArrayList<>();
                for (TreeNode cur : childMap.get(currentNode.getHierarchy())) {
                    currentCodes.add(cur.getHierarchy());
                }

                for (TreeNode child : children) {
                    if (!currentCodes.contains(child.getHierarchy())) {
                        childMap.get(currentNode.getHierarchy()).add(child);
                    }
                }
            } else {
                childMap.put(currentNode.getHierarchy(), children);
            }

            processedNodes += 1;
        }

        return new FlatTree<>(childMap, parentMap);
    }

}
