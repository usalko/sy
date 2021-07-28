package io.github.usalko.sy.exception;

import io.dropwizard.jersey.errors.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    public Response toResponse(Throwable ex) {

        ErrorMessage errorMessage = new ErrorMessage(ex.toString());

        return Response.status(errorMessage.getCode())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}