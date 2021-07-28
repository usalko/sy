package io.github.usalko.sy.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GeometryShape {

    private long id;

    private String mnemonic;

    public GeometryShape(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}
