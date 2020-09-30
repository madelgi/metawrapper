package com.docone.metawrapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TreeNode implements Serializable {
    private String hierarchy;
    private String name;
    private String code;

    @JsonProperty
    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "hierarchy='" + hierarchy + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }


}