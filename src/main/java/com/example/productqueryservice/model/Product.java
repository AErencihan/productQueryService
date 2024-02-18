package com.example.productqueryservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @Id
    private Long id;

    @Field(name = "productName")
    private String productName;

    @Field(name = "createTime", type = FieldType.Date)
    private LocalDateTime createTime;


    @Field(name = "status")
    private String status;


    @Field(name = "imageUrl")
    private String imageUrl;


}

