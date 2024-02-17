package com.example.productqueryservice.model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Product {


    @Id
    private Long id;

    @Field(name = "productName")
    private String productName;

    @CreationTimestamp
    @Field(name = "createTime", type = FieldType.Date)
    private LocalDateTime createTime;


    @Field(name = "status")
    private String status;


    @Field(name = "imageUrl")
    private String imageUrl;


}

