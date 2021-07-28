package io.github.usalko.sy.domain;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class Token {

    String id;

    LocalDateTime created;

}
