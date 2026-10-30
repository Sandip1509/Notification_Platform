package com.technologyzer.notificationapi.models.request;

import lombok.Data;

@Data
public class SortRequest {

    private String sortKey;
    private SortType sortType;

}
