// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

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