package com.example.productqueryservice.api;

import com.example.productqueryservice.dto.SearchRequestDto;
import com.example.productqueryservice.model.Product;
import com.example.productqueryservice.service.ElasticConsumerService;
import com.example.productqueryservice.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ElasticsearchService elasticsearchService;



    @GetMapping("/searchName")
    public Iterable<Product> getAllProducts(@RequestBody SearchRequestDto dto) {
        return elasticsearchService.searchProduct(dto.getQuery(),"productName", dto.getSize(), dto.getPage());
    }

    @GetMapping("/searchStatus")
    public List<Product> searchProductByStatus(@RequestBody SearchRequestDto dto){
        return elasticsearchService.searchProduct(dto.getQuery(),"status", dto.getSize(), dto.getPage());
    }


    @GetMapping("/searchNameAndStatus")
    public List<Product> searchProductByNameAndStatusWithQuery(@RequestBody SearchRequestDto dto){
        return elasticsearchService.searchProductByNameAndStatusWithQuery(dto.getQuery(), "productName", "status",
                dto.getSize(), dto.getPage());
    }



}






















