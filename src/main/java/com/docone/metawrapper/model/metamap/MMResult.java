package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.Result;

import java.util.List;

public class MMResult {

    private List<MMAcronym> acronyms;
    //private List<Negation> negations;
    private List<MMUtterance> utterances;

    public MMResult(Result result) throws Exception {
        this.acronyms = MMAcronym.mmAcronymList(result.getAcronymsAbbrevs());
        //this.negations = negations;
        this.utterances = MMUtterance.mmUtteranceList(result.getUtteranceList());
    }

    @JsonProperty
    public List<MMAcronym> getAcronyms() {
        return this.acronyms;
    }

    //@JsonProperty
    //public List<Negation> getNegations() {
    //    return this.negations;
    //}

    @JsonProperty
    public List<MMUtterance> getUtterances() {
        return this.utterances;
    }
}
