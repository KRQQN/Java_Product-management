package com.example.jakarta.mappers;

import com.example.jakarta.API.ProductsResource;
import com.example.jakarta.exceptions.ConstraintViolationException;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = LoggerFactory.getLogger(ProductsResource.class);

    @Override
    public Response toResponse(ConstraintViolationException e) {
        String vio = e.getMessage();
        //Set<ConstraintViolation<?>> constraintViolations = e.getMessage();
        JsonObject errorJsonEntity = Json.createObjectBuilder()
                .add("host", "HUEEEE???")
                .add("resource", "uriInfo.getAbsolutePath().getPath()")
                .add("title", "nöhh")
                .build();
        /*.add("errors", (JsonValue) constraintViolations.stream()
                        .map(constraint -> Json.createObjectBuilder()
                                .add("class", constraint.getLeafBean().toString().split("@")[0])
                                .add("field", constraint.getPropertyPath().toString().split("\\.")[2])
                                .add("violationMessage", constraint.getMessage())
                                .build()
                        )
                        .toList()
                )*/



        logger.info("HÄR ÄR VI "+ ProductsResource.class);
        return Response.status(Response.Status.NOT_IMPLEMENTED).entity(errorJsonEntity.entrySet()).build();
    }
}
