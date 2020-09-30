package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;

import java.util.ArrayList;
import java.util.List;

public class MMAcronym {

    private String acronym;
    private List<Integer> countList;
    private List<String> cuiList;
    private String expansion;

    public MMAcronym(AcronymsAbbrevs aa) {
        this.acronym = aa.getAcronym();
        this.countList = aa.getCountList();
        this.cuiList = aa.getCUIList();
        this.expansion = aa.getExpansion();
    }

    public static List<MMAcronym> mmAcronymList(List<AcronymsAbbrevs> acronyms) {
        List<MMAcronym> acs = new ArrayList<MMAcronym>();
        for (AcronymsAbbrevs acronym : acronyms) {
            acs.add(new MMAcronym(acronym));
        }

        return acs;
    }

    @JsonProperty
    public String getAcronym() {
        return this.acronym;
    }

    @JsonProperty
    public List<Integer> getCountList() {
        return this.countList;
    }

    @JsonProperty
    public List<String> getCuiList() {
        return cuiList;
    }

    @JsonProperty
    public String getExpansion() {
        return expansion;
    }

}
