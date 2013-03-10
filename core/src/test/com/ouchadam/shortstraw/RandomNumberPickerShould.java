package com.ouchadam.shortstraw;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class RandomNumberPickerShould {

    private static final int RETRY_AMOUNT = 50;
    private RandomNumberPicker randomNumberPicker;

    @Before
    public void setUp() throws Exception {
        randomNumberPicker = new RandomNumberPicker();
    }

    @Test
    public void never_pick_a_number_higher_than_the_max() throws Exception {
        int max = 12;
        for (int i = 0; i < RETRY_AMOUNT; i++) {
            int randomNumber = randomNumberPicker.getRandomNumber(max);

            assertThat(randomNumber).isLessThan(max + 1);
        }
    }

}
