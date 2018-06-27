package com.blacar.spike.userapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {
    private String resource;
    private String field;
    private Object value;

    public ResourceNotFoundException(
        final String resourceparam,
        final String fieldparam,
        final Object valueparam
    ) {
        super(
            String.format(
                "%s not found with %s : '%s'",
                resourceparam,
                fieldparam,
                valueparam
            )
        );
        this.resource = resourceparam;
        this.field = fieldparam;
        this.value = valueparam;
    }

    public String getResource() {
        return resource;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
