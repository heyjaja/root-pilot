package com.root.pilot.commons;

import java.util.Random;


public class RandomStringUtils {

    public static String random(int size) {
        Random random = new Random();

        return random.ints(97, 123)
            .limit(size)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
