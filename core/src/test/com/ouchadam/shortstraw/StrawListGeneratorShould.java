package com.ouchadam.shortstraw;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class StrawListGeneratorShould {

    private static final int GENERATE_RETRY_AMOUNT = 10;
    private final Straw shortStraw = new Straw(true);
    private StrawListGenerator strawListGenerator;

    @Before
    public void setUp() throws Exception {
        strawListGenerator = new StrawListGenerator();
    }

    @Test
    public void always_pick_a_short_straw_when_there_is_only_one_straw() throws Exception {
        int strawTotal = 1;
        for (int i = 0; i < GENERATE_RETRY_AMOUNT; i++) {
            List<Straw> straws = strawListGenerator.generate(strawTotal);

            assertThat(straws).contains(shortStraw);
        }
    }

    @Test
    public void always_pick_a_short_straw() throws Exception {
        int strawTotal = 200;
        for (int i = 0; i < GENERATE_RETRY_AMOUNT; i++) {
            List<Straw> straws = strawListGenerator.generate(strawTotal);

            assertThat(straws).contains(shortStraw);
        }
    }
}
