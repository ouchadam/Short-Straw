package com.ouchadam.shortstraw;

import java.util.ArrayList;
import java.util.List;

public class StrawListGenerator {

    private static final int ZERO_INDEX_OFFSET = 1;
    private static final int EDGE_CASE_SINGLE_STRAW = 1;

    private final List<Straw> straws;

    public StrawListGenerator() {
        straws = new ArrayList<Straw>();
    }

    public List<Straw> generate(int strawTotal) {
        if (isEdgeCase(strawTotal)) {
            return createSingleStraw();
        } else {
            return createStraws(strawTotal);
        }
    }

    private List<Straw> createSingleStraw() {
        addShortStraw();
        return straws;
    }

    private List<Straw> createStraws(int strawTotal) {
        int shortStraw = getShortStraw(strawTotal - ZERO_INDEX_OFFSET);
        for (int i = 0; i < strawTotal; i++) {
            if (i == shortStraw) {
                addShortStraw();
            } else {
                straws.add(new Straw(false));
            }
        }
        return straws;
    }

    private void addShortStraw() {
        straws.add(new Straw(true));
    }

    private boolean isEdgeCase(int strawTotal) {
        return strawTotal == EDGE_CASE_SINGLE_STRAW;
    }

    private int getShortStraw(int strawTotal) {
        return (int) (Math.random() * (strawTotal + 1));
    }

}
