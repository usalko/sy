package io.github.usalko.sy.convert;

import io.github.usalko.sy.model.SharedMood;

import java.util.Map;

public class SharedMoodConvertor {

    private final Map<String, Object> source;

    public SharedMoodConvertor(Map<String, Object> source) {
        this.source = source;
    }

    public SharedMood convert() {
        SharedMood sharedMood = new SharedMood();
        return sharedMood;
    }
}
