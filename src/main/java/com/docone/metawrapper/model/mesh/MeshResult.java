package com.docone.metawrapper.model.mesh;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MeshResult {

    private Integer score;
    private String uniqueId;
    private String name;

    public MeshResult(Integer score, String uniqueId, String name) {
        this.score = score;
        this.uniqueId = uniqueId;
        this.name = name;
    }


    @JsonProperty
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @JsonProperty
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
