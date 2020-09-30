package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.Phrase;

public class MMPhrase {

    private String mincoMan;
    private String text;
    private MMPosition position;

    public MMPhrase() { }

    public MMPhrase(Phrase phrase) throws Exception {
        this.mincoMan = phrase.getMincoManAsString();
        this.text = phrase.getPhraseText();
        this.position = new MMPosition(phrase.getPosition());
    }

    @JsonProperty
    public String getMincoMan() {
        return mincoMan;
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    @JsonProperty
    public MMPosition getPosition() {
        return position;
    }

    public void setMincoMan(String mincoMan) {
        this.mincoMan = mincoMan;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(MMPosition position) {
        this.position = position;
    }
}
