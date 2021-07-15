package io.github.usalko.sy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "geometry_shapes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"mnemonic"}))
public class GeometryShape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "GeometryShape mnemonic is required.")
    @Basic(optional = false)
    private String mnemonic;

    public GeometryShape(Long id, @NotNull(message = "GeometryShape mnemonic is required.") String mnemonic) {
        this.id = id;
        this.mnemonic = mnemonic;
    }

    public GeometryShape() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}
