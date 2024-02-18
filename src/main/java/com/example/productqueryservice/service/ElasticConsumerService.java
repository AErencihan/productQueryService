package com.example.productqueryservice.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.productqueryservice.model.Product;
import com.example.productqueryservice.repository.ProductRepository;
import com.example.productqueryservice.util.ESUtil;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticConsumerService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final ElasticsearchClient elasticsearchClient;




    @KafkaListener(topics = "topicProduct", groupId = "group_Id")
    public void createProduct(String message) {
        try {
            log.info("Consumed message: {}", message);
            Product product = objectMapper.readValue(message, Product.class);
            productRepository.save(product);
            log.info("Product saved in Elasticsearch: {}", product);

        }catch (Exception e) {
            log.error("Error consuming message: {}", e.getMessage());
        }
    }




    public List<Product> getALlProducts(String productName, int size, int page) {
        Query query = ESUtil.createMatchNameQuery(productName, size, page);
        log.info("elasticsearch query: {}", query.toString());
        SearchResponse<Product> response = null; // ElasticClient'ın kendi response type'ı.
        try {
            response = elasticsearchClient.search(q -> q.query(query), Product.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Elasticsearch response: {}", response.toString());
//-------------
        return extractProductsFromResponse(response);
    }

    public List<Product> extractProductsFromResponse(SearchResponse<Product> response) {
        return response
                //eşleşen veriler hits'e geliyor.Bir ana başlık gibi
                .hits()
                //sonrasında ise bir alta list şeklinde geliyor.
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList()); //en son burada bir list Product'a çevrilir

    }


}
