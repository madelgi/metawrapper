package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.PCM;

import java.util.ArrayList;
import java.util.List;

public class MMPcm {

    // TODO mapping
    private List<MMCandidate> candidates;
    private MMPhrase phrase;

    public MMPcm() {}

    public MMPcm(PCM pcm) throws Exception {
        this.candidates = MMCandidate.mmCandidateList(pcm.getCandidateList());
        this.phrase = new MMPhrase(pcm.getPhrase());
    }

    public static List<MMPcm> mmPcmList(List<PCM> pcms) throws Exception {
        List<MMPcm> mmPcms = new ArrayList<MMPcm>();
        for (PCM pcm : pcms) {
            mmPcms.add(new MMPcm(pcm));
        }

        return mmPcms;
    }

    @JsonProperty
    public List<MMCandidate> getCandidates() {
        return candidates;
    }

    @JsonProperty
    public MMPhrase getPhrase() {
        return phrase;
    }

    public void setCandidates(List<MMCandidate> candidates) {
        this.candidates = candidates;
    }

    public void setPhrase(MMPhrase phrase) {
        this.phrase = phrase;
    }
}
