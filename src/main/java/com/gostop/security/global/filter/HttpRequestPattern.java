package com.gostop.security.global.filter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HttpRequestPattern {
    private String httpMethod;
    private String uriPattern;
}
