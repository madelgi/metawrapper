package com.docone.metawrapper.service;

import com.docone.metawrapper.dao.MRConsoDao;
import com.docone.metawrapper.model.TreeNode;
import com.docone.metawrapper.util.FlatTree;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MeshTreeService {

    // MeSH roots
    private static final String BACTERIAL_INFECTIONS_MYCOSES = "C01";
    private static final String VIRUS_DISEASES = "C02";
    private static final String PARASITIC_DISEASES = "C03";
    private static final String NEOPLASMS = "C04";
    private static final String MUSCULOSKELETAL_DISEASES = "C05";
    private static final String DIGESTIVE_SYSTEM_DISEASES = "C06";
    private static final String STOMATOGNATHIC_DISEASES = "C07";
    private static final String RESPIRATORY_TRACT_DISEASES = "C08";
    private static final String OTORHINOLARYNGOLOGIC_DISEASES = "C09";
    private static final String NERVOUS_SYSTEM_DISEASES = "C10";
    private static final String EYE_DISEASES = "C11";
    private static final String MALE_UROGENITAL_DISEASES = "C12";
    private static final String FEMALE_UROGENITAL_DISEASES_PREGNANCY_COMPLICATIONS = "C13";
    private static final String CARDIOVASCULAR_DISEASES = "C14";
    private static final String HEMIC_LYMPHATIC_DISEASES = "C15";
    private static final String CONGENITAL_HEREDITARY_NEONATAL_DISEASES_ABNORMALITIES = "C16";
    private static final String SKIN_CONNECTIVE_TISSUE_DISEASES = "C17";
    private static final String NUTRITIONAL_METABOLIC_DISEASES = "C18";
    private static final String ENDOCRINE_SYSTEM_DISEASES = "C19";
    private static final String IMMUNE_SYSTEM_DISEASES = "C20";
    private static final String DISORDERS_OF_ENVIRONMENTAL_ORIGIN = "C21";
    private static final String ANIMAL_DISEASES = "C22";
    private static final String PATHOLOGICAL_CONDITIONS_SIGNS_SYMPTOMS = "C23";
    private static final String OCCUPTIONAL_DISEASES = "C24";
    private static final String CHEMICALLY_INDUCED_DISORDERS = "C25";
    private static final String WOUNDS_INJURIES = "C26";

    private static final String BEHAVIOR_AND_BEHAVIOR_MECHANISMS = "F01";
    private static final String PSYCHOLOGICAL_PHENOMENA = "F02";
    private static final String MENTAL_DISORDERS = "F03";
    private static final String BEHAVIORAL_DISCIPLINES_AND_ACTIVITIES = "F04";


    private static final String ROOT_NAME = "ROOT";
    private static final String DISEASE_NODE = "C";
    private static final String PSYCH_NODE = "F";

    private static final String FPATH = System.getProperty("user.home") + "/.cache/";
    private static final String FNAME = "mesh_flat_tree.cer";
    private static final Logger LOGGER = Logger.getLogger(RXService.class.getName());

    private MRConsoDao mrConsoDao;
    private FlatTree<List<TreeNode>> flatTree;

    public MeshTreeService(MRConsoDao mrConsoDao) {
        this.mrConsoDao = mrConsoDao;

        LOGGER.info("Constructing MeSH tree data");
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
            LOGGER.info("Loading MeshTreeService from cache.");
            try (InputStream file = new FileInputStream(FPATH + FNAME);
                 InputStream buffer = new BufferedInputStream(file);
                 ObjectInput input = new ObjectInputStream(buffer)
            ) {
                this.flatTree = (FlatTree<List<TreeNode>>) input.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex);
                LOGGER.warning("Unable to deserialize MeshTreeService data. Rebuilding...");
                buildAndSaveFlatTree();
            }

        } else {
            buildAndSaveFlatTree();
        }
    }

    public void buildAndSaveFlatTree() {
        LOGGER.info("Building/saving MeshTreeService data.");
        this.flatTree = buildFlatTree();
        try (OutputStream file = new FileOutputStream(FPATH + FNAME);
             OutputStream buffer = new BufferedOutputStream(file);
             ObjectOutput output = new ObjectOutputStream(buffer)
        ) {
            output.writeObject(this.flatTree);
        } catch (IOException ex) {
            // DO nothing
            LOGGER.warning("Unable to serialize MeshTreeService data.");
        }
    }

    public List<TreeNode> getParents(List<String> hcds) {
        List<TreeNode> parents = new ArrayList<>();

        for (String hcd : hcds) {

        }

        return parents;
    }

    /**
     *
     * @param hcd
     * @return List of MeshTreeNodes.
     */
    public List<TreeNode> getParents(String hcd) {
        TreeNode currentNode = getNode(hcd);
        String hierarchy = currentNode.getHierarchy();
        String[] levels = hierarchy.split(Pattern.quote("."));

        if (levels.length == 0) {
            return new ArrayList<>();
        }

        switch (levels[0]) {
            // Up to level 3
            case VIRUS_DISEASES:
            case PARASITIC_DISEASES:
            case MUSCULOSKELETAL_DISEASES:
            case DIGESTIVE_SYSTEM_DISEASES:
            case STOMATOGNATHIC_DISEASES:
            case RESPIRATORY_TRACT_DISEASES:
            case OTORHINOLARYNGOLOGIC_DISEASES:
            case NERVOUS_SYSTEM_DISEASES:
            case EYE_DISEASES:
            case CARDIOVASCULAR_DISEASES:
            case HEMIC_LYMPHATIC_DISEASES:
            case CONGENITAL_HEREDITARY_NEONATAL_DISEASES_ABNORMALITIES:
            case SKIN_CONNECTIVE_TISSUE_DISEASES:
            case NUTRITIONAL_METABOLIC_DISEASES:
            case ENDOCRINE_SYSTEM_DISEASES:
            case IMMUNE_SYSTEM_DISEASES:
            case DISORDERS_OF_ENVIRONMENTAL_ORIGIN:
            case ANIMAL_DISEASES:
            case PATHOLOGICAL_CONDITIONS_SIGNS_SYMPTOMS:
            case OCCUPTIONAL_DISEASES:
            case CHEMICALLY_INDUCED_DISORDERS:
            case WOUNDS_INJURIES:
            case BEHAVIOR_AND_BEHAVIOR_MECHANISMS:
            case PSYCHOLOGICAL_PHENOMENA:
            case MENTAL_DISORDERS:
            case BEHAVIORAL_DISCIPLINES_AND_ACTIVITIES:
                return getParentsHelper(currentNode, 3);
            // Up to level 4
            case BACTERIAL_INFECTIONS_MYCOSES:
            case MALE_UROGENITAL_DISEASES:
            case NEOPLASMS:
                return getParentsHelper(currentNode, 4);
            // Up to level 6
            case FEMALE_UROGENITAL_DISEASES_PREGNANCY_COMPLICATIONS:
                return getParentsHelper(currentNode, 6);
            default:
                return getParentsHelper(currentNode, null);
        }
    }

    private List<TreeNode> getParentsHelper(TreeNode node, Integer maxLevel) {
        int currentLevel = node.getHierarchy().split(Pattern.quote(".")).length;
        TreeNode currentNode = node;
        List<TreeNode> parents = Arrays.asList(node);

        if (maxLevel == null || currentLevel > maxLevel) {
            return parents;
        }

        for (int i = 0; i < (maxLevel - currentLevel); i++) {
            String parentCode = flatTree.getParent(currentNode.getHierarchy());
            TreeNode parentNode = getNode(parentCode);
            parents.add(parentNode);
            currentNode = parentNode;
        }

        return parents;
    }

    public List<TreeNode> getChildren(String code) {
        return flatTree.getChildren(code);
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


    public TreeNode getNode(String code) {
        List<TreeNode> sibs = flatTree.getSiblings(code);
        return sibs.stream().filter(x -> x.getCode().equals(code)).collect(Collectors.toList()).get(0);
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
        List<TreeNode> initialMesh = new ArrayList<>();
        initialMesh.addAll(mrConsoDao.getMeshRootChildren(DISEASE_NODE));
        initialMesh.addAll(mrConsoDao.getMeshRootChildren(PSYCH_NODE));
        childMap.put(ROOT_NAME, initialMesh);

        for (TreeNode mesh : initialMesh) {
            parentMap.put(mesh.getHierarchy(), ROOT_NAME);
            stack.add(mesh);
        }

        int processedNodes = 0;
        while (!stack.isEmpty()) {
            TreeNode currentHcd = stack.pop();

            // TODO just for progress, can probably remove after development
            if (processedNodes % 500 == 0) {
                LOGGER.info("Processed " + processedNodes + " MeSH terms.");
            }

            // NOTE: I think this should only happen if the tree has a loop, but I don't think that will ever happen
            if (seen.containsKey(currentHcd.getHierarchy())) {
                continue;
            } else {
                seen.put(currentHcd.getHierarchy(), true);
            }

            List<TreeNode> children = mrConsoDao.getMeshChildrenFromHcd(currentHcd.getHierarchy());

            if (!children.isEmpty()) {
                for (TreeNode childMesh : children) {
                    parentMap.put(childMesh.getHierarchy(), currentHcd.getHierarchy());
                }
                stack.addAll(children);
            }

            // Multiple hierarchical structures --
            if (childMap.containsKey(currentHcd.getHierarchy())) {
                LOGGER.info("THIS HAPPENS: " + currentHcd.getHierarchy());
                List<String> currentCodes = new ArrayList<>();
                for (TreeNode cur : childMap.get(currentHcd.getHierarchy())) {
                    currentCodes.add(cur.getHierarchy());
                }

                for (TreeNode child : children) {
                    if (!currentCodes.contains(child.getHierarchy())) {
                        childMap.get(currentHcd.getHierarchy()).add(child);
                    }
                }
            } else {
                childMap.put(currentHcd.getHierarchy(), children);
            }

            processedNodes += 1;
        }

        return new FlatTree<>(childMap, parentMap);
    }
}
