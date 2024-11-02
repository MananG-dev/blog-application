package com.blogapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String source, String field, Object data) {
        super(source + " not found with " + field + " : " + data.toString());
        this.resourceName = source;
        this.fieldName = field;
        this.fieldValue = data;
    }
}
