package io.github.usalko.sy.resources;

import io.github.usalko.sy.domain.GeometryShape;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Geometry")
@Produces(MediaType.APPLICATION_JSON)
public class GeometryResource {

    @GET
    public List<GeometryShape> list() {
        return Arrays.asList(new GeometryShape("triangle"),
                new GeometryShape("square"),
                new GeometryShape("circle"));
    }

}