package io.github.usalko.sy.model;

import javax.persistence.*;

@Entity
@Table(name = "geometry_forms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"mnemonic"}))
public class GeometryForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mnemonic;
}
