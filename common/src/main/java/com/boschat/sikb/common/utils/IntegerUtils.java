package com.boschat.sikb.common.utils;

import java.util.List;

public class IntegerUtils {

    private IntegerUtils() {

    }

    public static Integer[] toIntegerArray(List<Integer> values) {
        Integer[] array = null;
        if (values != null) {
            array = new Integer[values.size()];
            for (int i = 0; values.size() > i; i++) {
                array[i] = values.get(i);
            }
        }
        return array;
    }
}
