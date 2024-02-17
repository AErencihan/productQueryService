package com.example.productqueryservice.service;


import com.example.productqueryservice.model.Product;
import com.example.productqueryservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticsearchService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ElasticsearchService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "topicProduct", groupId = "group_id")
    public void consume(String message) {
        try {
            log.info("Consumed message: {}", message);
            Product product = objectMapper.readValue(message, Product.class);
            productRepository.save(product);
            log.info("Product saved in Elasticsearch: {}", product);

        }catch (Exception e) {
            log.error("Error consuming message: {}", e.getMessage());
        }
    }


}
