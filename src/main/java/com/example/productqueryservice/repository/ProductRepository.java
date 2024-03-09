package com.example.productqueryservice.repository;


import com.example.productqueryservice.model.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, Long> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"productName\": \"?0\"}},{\"match\": {\"status\": \"?1\"}}]}}")
    List<Product> searchByProductNameAndStatus(String query, String productName, String status, int size, int page);
}

