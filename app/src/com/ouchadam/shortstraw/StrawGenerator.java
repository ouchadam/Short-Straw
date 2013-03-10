package com.ouchadam.shortstraw;

import java.util.ArrayList;
import java.util.List;

public class StrawGenerator {

    public static List<Straw> generate(int strawTotal) {
        List<Straw> straws = new ArrayList<Straw>(strawTotal);
        int shortStraw = (int) (Math.random() * (strawTotal + 1));
        for (int i = 0; i < strawTotal; i++) {
            if (i == shortStraw) {
                straws.add(new Straw(true));
            } else {
                straws.add(new Straw(false));
            }
        }
        return straws;
    }

    public static class Straw {

        private final boolean isShort;

        public Straw(boolean isShort) {
            this.isShort = isShort;
        }

        public boolean isShort() {
            return isShort;
        }

    }

}
