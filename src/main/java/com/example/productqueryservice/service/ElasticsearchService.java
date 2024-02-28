package com.example.productqueryservice.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.productqueryservice.dto.SearchRequestDto;
import com.example.productqueryservice.model.Product;
import com.example.productqueryservice.repository.ProductRepository;
import com.example.productqueryservice.util.ESUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticsearchService {

    private final ElasticsearchClient elasticsearchClient;
    private final ProductRepository productRepository;



    public List<Product> searchProduct(String productName, String field, int size, int page) {
        Query query = ESUtil.createMatchNameQuery(productName, field, size, page);
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


    public List<Product> searchProductByNameAndStatusWithQuery(String query, String productName, String status, int size, int page) {
        return productRepository.searchByProductNameAndStatus(query, productName, status, size, page);
    }


    public List<Product> searchProductNameAndStatusWithBool(SearchRequestDto dto){
        var query = ESUtil.createBoolQuery(dto);
        log.info("elasticsearch query: {}", query.toString());
        SearchResponse<Product> response = null;

        try {
            response = elasticsearchClient.search(q -> q.index("product").query(query.get()), Product.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("elasticsearch query: {}", query.toString());
        return  extractProductsFromResponse(response); //searchResponse'un içerisini parçalayarak istediğimiz şeyin dönmesini sağlayacak

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
