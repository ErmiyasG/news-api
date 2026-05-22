package org.afrolink.er.news_api.common.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedResponse<T> {

    private boolean success;
    private String message;

    private List<T> object;

    private int pageNumber;
    private int pageSize;
    private long totalSize;

    private List<String> errors;
}