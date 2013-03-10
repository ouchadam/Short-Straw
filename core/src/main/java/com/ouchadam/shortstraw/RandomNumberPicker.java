package com.ouchadam.shortstraw;

public class RandomNumberPicker {

    public int getRandomNumber(int max) {
        return (int) (Math.random() * (max + 1));
    }

}