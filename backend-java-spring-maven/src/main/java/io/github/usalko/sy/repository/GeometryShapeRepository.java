package io.github.usalko.sy.repository;

import io.github.usalko.sy.model.GeometryShape;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GeometryShapeRepository extends CrudRepository<GeometryShape, Long> {
    List<GeometryShape> findGeometryShapeByMnemonic(String mnemonic);
}