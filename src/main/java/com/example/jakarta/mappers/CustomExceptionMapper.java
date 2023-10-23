package com.example.jakarta.mappers;

import com.example.jakarta.entities.ErrorResponseMessage;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jakarta.ws.rs.core.Response.Status.*;
@Provider
public class CustomExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException e) {

        final var errors = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage() + " @ "
                        + violation.getRootBeanClass())
                .toList();

        logger.info(errors.toString());
        return Response.status(BAD_REQUEST).entity(new ErrorResponseMessage(errors)).build();
    }
}




