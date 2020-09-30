package com.docone.metawrapper.service;

import com.docone.metawrapper.dao.MRConsoDao;
import com.docone.metawrapper.model.TreeNode;
import com.docone.metawrapper.model.umls.RXData;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RXService {
    private static final String CODE_INPUT = "CODE";
    private static final String STRING_INPUT = "STR";
    private static final String MAY_TREAT = "may_treat";
    private static final String MAY_BE_TREATED_BY = "may_be_treated_by";

    private static final Logger LOGGER = Logger.getLogger(RXService.class.getName());

    private MRConsoDao mrConsoDao;
    private MeshTreeService meshService;

    public RXService(MRConsoDao mrConsoDao) {
        this.mrConsoDao = mrConsoDao;
        this.meshService = new MeshTreeService(this.mrConsoDao);
    }

    public List<RXData> getRxFromCondition(String condition, String inputType, String source) throws IllegalArgumentException {
        List<String> parentHcds;
        String parentCode;

        // Get parent code
        if (inputType.equals(CODE_INPUT)) {
            LOGGER.info("Get RX from condition code \"" + condition + "\", source \"" + source + "\"");
            parentCode = condition;
            parentHcds = mrConsoDao.getHcdsFromCode(parentCode);
        } else if (inputType.equals(STRING_INPUT)) {
            LOGGER.info("Get RX from string \"" + condition + "\", source \"" + source + "\"");
            parentCode = mrConsoDao.getMeshCodeFromString(condition);
            if (parentCode == null) {
                IllegalArgumentException e = new IllegalArgumentException("String '" + condition + "' does not correspond to any known unique ID.");
                throw e;
            }
            parentHcds = mrConsoDao.getHcdsFromCode(parentCode);
        } else {
            throw new IllegalArgumentException("Unrecognized inputType \"" + inputType + "\"");
        }

        if (parentHcds == null || parentHcds.isEmpty()) {
            throw new IllegalArgumentException("Unique ID '" + parentCode + "' does not correspond to HCD(s).");
        }

        // Get children
        List<TreeNode> children = meshService.getAllChildren(parentHcds);
        List<TreeNode> parents = meshService.getParents(parentHcds);

        if (!children.isEmpty() || !parents.isEmpty()) {
            List<String> codes = children.stream().map(TreeNode::getCode).collect(Collectors.toList());
            codes.addAll(parents.stream().map(TreeNode::getCode).collect(Collectors.toList()));

            // Get drugs
            return mrConsoDao.getRxFromCodes(codes);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Get a list of related conditions from drug.
     *
     * @param drug The drug to query.
     * @param inputType Can be one of "STR" or "CODE", defaults to "STR".
     * @param source The source database for conditions. Defaults to "MSH".
     *
     * @return A map.
     * @throws Exception
     */
    public List<TreeNode> getConditionFromRX(String drug, String inputType, String source) throws Exception {
        if (inputType.equals(CODE_INPUT)) {
            LOGGER.info("Get condition from RX code \"" + drug + "\", source \"" + source + "\"");
        } else if (inputType.equals(STRING_INPUT)) {
            LOGGER.info("Get condition from RX string \"" + drug + "\", source \"" + source + "\"");
        } else {
            throw new Exception("Unrecognized inputType");
        }

        // TODO
        //for (AbstractMap.SimpleEntry<String, String> entry : conditions) {
        //    if (entry.getValue().equals(MAY_BE_TREATED_BY)) {
        //        conditionMap.get("mayBeTreatedBy").add(entry.getKey());
        //    } else {
        //        conditionMap.get("mayBePreventedBy").add(entry.getKey());
        //    }
        //}

        //return conditionMap;

        return null;
    }
}
