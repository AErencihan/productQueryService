package com.example.productqueryservice.repository;


import com.example.productqueryservice.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, Long> {





}
