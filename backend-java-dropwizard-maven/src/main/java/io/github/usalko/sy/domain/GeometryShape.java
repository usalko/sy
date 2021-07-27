package io.github.usalko.sy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeometryShape {

    @JsonProperty("mnemonic")
    public final String mnemonic;

    public GeometryShape(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}
