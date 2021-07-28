package io.github.usalko.sy.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeometryShape {

    private long id;

    private String mnemonic;

    @JsonCreator
    public GeometryShape(@JsonProperty("mnemonic") String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public GeometryShape() {
    }
}
