package io.github.usalko.sy.resources;

import io.github.usalko.sy.db.GeometryShapeDao;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Geometry")
@Produces(MediaType.APPLICATION_JSON)
public class GeometryResource {

    private final GeometryShapeDao geometryShapeDao;

    @Inject
    public GeometryResource(GeometryShapeDao geometryShapeDao) {
        this.geometryShapeDao = geometryShapeDao;
    }

    @GET
    public List<String> list() {
        return geometryShapeDao.findAllMnemonics();
    }

}