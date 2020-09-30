package com.docone.metawrapper.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class FlatTree<T extends Collection> implements Serializable {

    private Map<String, T> childMap;
    private Map<String, String> parentMap;


    public FlatTree(Map<String, T> childMap, Map<String, String> parentMap) {
        this.childMap = childMap;
        this.parentMap = parentMap;
    }

    public T getSiblings(String code) {
        String parent = parentMap.get(code);
        return childMap.get(parent);
    }

    public T getChildren(String parent) {
        return childMap.get(parent);
    }

    public String getParent(String child) {
        return parentMap.get(child);
    }

    public Map<String, T> getChildMap() {
        return childMap;
    }

    public Map<String, String> getParentMap() {
        return parentMap;
    }
}
