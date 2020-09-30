package com.docone.metawrapper.model.cpt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CPTTree {
    private String code;
    private Integer count;
    private List<CPTTree> leaves;

    public CPTTree() {}

    public CPTTree(String code, Integer count, List<CPTTree> leaves) {
        this.code = code;
        this.count = count;
        this.leaves = leaves;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    @JsonProperty
    public Integer getCount() {
        return count;
    }

    @JsonProperty
    public List<CPTTree> getLeaves() {
        return leaves;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setLeaves(List<CPTTree> leaves) {
        this.leaves = leaves;
    }
}
