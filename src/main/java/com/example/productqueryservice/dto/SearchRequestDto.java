package com.example.productqueryservice.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SearchRequestDto {
    private String productName;
    private String status;
    private String query;
    private int size = 10;
    private int page = 0;

}
