package org.afrolink.er.news_api.common.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T object;
    private List<String> errors;
}
