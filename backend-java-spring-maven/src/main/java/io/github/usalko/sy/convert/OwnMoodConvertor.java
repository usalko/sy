package io.github.usalko.sy.convert;

import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.SharedMood;

import java.util.Map;

public class OwnMoodConvertor {

    private final Map<String, Object> source;

    public OwnMoodConvertor(Map<String, Object> source) {
        this.source = source;
    }

    public OwnMood convert() {
        OwnMood ownMood = new OwnMood();
        return ownMood;
    }
}
