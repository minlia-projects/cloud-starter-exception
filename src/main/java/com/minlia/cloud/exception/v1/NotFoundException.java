package com.minlia.cloud.exception.v1;

import static java.lang.String.format;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String api) {
        super(format("Api %s does not exist", api));
    }
}
