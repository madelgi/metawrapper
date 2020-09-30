package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.nlm.nls.metamap.Position;

import java.util.ArrayList;
import java.util.List;

public class MMPosition {
    private int x;
    private int y;

    public MMPosition() {}

    public MMPosition(Position pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public static List<MMPosition> mmPositionList(List<Position> positions) {
        List<MMPosition> mmPositions = new ArrayList<MMPosition>();
        for (Position position : positions) {
            mmPositions.add(new MMPosition(position));
        }

        return mmPositions;
    }

    @JsonProperty
    public int getX() {
        return x;
    }

    @JsonProperty
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
