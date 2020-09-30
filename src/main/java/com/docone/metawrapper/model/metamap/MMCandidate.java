package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.Ev;

import java.util.ArrayList;
import java.util.List;

public class MMCandidate {

    private int score;
    private String conceptId;
    private String conceptName;
    private String preferredName;
    private List<String> matchedWords;
    private List<String> semanticTypes;
    private boolean head;
    private boolean overmatch;
    private List<String> sources;
    private List<MMPosition> positions;

    public MMCandidate() {}

    public MMCandidate(Ev candidate) throws Exception {
        // TODO add matchmap, matchmaplist
        this.score = candidate.getScore();
        this.conceptId = candidate.getConceptId();
        this.conceptName = candidate.getConceptName();
        this.preferredName = candidate.getPreferredName();
        this.matchedWords = candidate.getMatchedWords();
        this.semanticTypes = candidate.getSemanticTypes();
        this.head = candidate.isHead();
        this.overmatch = candidate.isOvermatch();
        this.sources = candidate.getSources();
        this.positions = MMPosition.mmPositionList(candidate.getPositionalInfo());
    }

    public static List<MMCandidate> mmCandidateList(List<Ev> candidates) throws Exception {
        List<MMCandidate> mmCandidates = new ArrayList<MMCandidate>();
        for (Ev candidate : candidates) {
            mmCandidates.add(new MMCandidate(candidate));
        }

        return mmCandidates;
    }

    @JsonProperty
    public int getScore() {
        return score;
    }

    @JsonProperty
    public String getConceptId() {
        return conceptId;
    }

    @JsonProperty
    public String getConceptName() {
        return conceptName;
    }

    @JsonProperty
    public String getPreferredName() {
        return preferredName;
    }

    @JsonProperty
    public List<String> getMatchedWords() {
        return matchedWords;
    }

    @JsonProperty
    public List<String> getSemanticTypes() {
        return semanticTypes;
    }

    @JsonProperty
    public boolean isHead() {
        return head;
    }

    @JsonProperty
    public boolean isOvermatch() {
        return overmatch;
    }

    @JsonProperty
    public List<String> getSources() {
        return sources;
    }

    @JsonProperty
    public List<MMPosition> getPositions() {
        return positions;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public void setMatchedWords(List<String> matchedWords) {
        this.matchedWords = matchedWords;
    }

    public void setSemanticTypes(List<String> semanticTypes) {
        this.semanticTypes = semanticTypes;
    }

    public void setHead(boolean head) {
        this.head = head;
    }

    public void setOvermatch(boolean overmatch) {
        this.overmatch = overmatch;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public void setPositions(List<MMPosition> positions) {
        this.positions = positions;
    }
}
