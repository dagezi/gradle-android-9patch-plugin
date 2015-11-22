package io.github.dagezi.ninepatch

import java.util.List
import java.util.Map

public class NinePatch {
    public String name
    public Map<Integer, Integer> stretchables = [:]
    public int paddingFrom
    public int paddingTo

    public NinePatch(String name) {
        this.name = name
    }

    public void stretch(int from, int to) {
        stretchables[from] = to
    }

    public void padding(int from, int to) {
        paddingFrom = from
        paddingTo = to
    }
}

