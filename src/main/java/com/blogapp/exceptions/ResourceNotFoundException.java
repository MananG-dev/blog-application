package com.blogapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String source, String field, Object data)  {
//        super(source + " not found with " + field + " having value " + data);
        super(String.format("%s not found with %s: " + data.toString()));
        this.resourceName = source;
        this.fieldName = field;
        this.fieldValue = data;
    }
}
