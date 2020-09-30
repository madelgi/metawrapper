package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.Utterance;

import java.util.ArrayList;
import java.util.List;

public class MMUtterance {

    private String id;
    private String text;
    private List<MMPcm> pcmList;
    private Position position;

    public MMUtterance() {}

    public MMUtterance(Utterance utterance) throws Exception {
        this.id = utterance.getId();
        this.text = utterance.getString();
        this.pcmList = MMPcm.mmPcmList(utterance.getPCMList());
        this.position = utterance.getPosition();
    }

    public static List<MMUtterance> mmUtteranceList(List<Utterance> utterances) throws Exception {
        List<MMUtterance> mmUtterances = new ArrayList<MMUtterance>();
        for (Utterance utterance : utterances) {
            mmUtterances.add(new MMUtterance(utterance));
        }

        return mmUtterances;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    @JsonProperty
    public List<MMPcm> getPcmList() {
        return pcmList;
    }

    @JsonProperty
    public Position getPosition() {
        return position;
    }
}
