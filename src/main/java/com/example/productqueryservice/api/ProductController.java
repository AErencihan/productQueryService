package com.example.productqueryservice.api;

import com.example.productqueryservice.dto.SearchRequestDto;
import com.example.productqueryservice.model.Product;
import com.example.productqueryservice.service.ElasticConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ElasticConsumerService elasticConsumerService;



    @GetMapping("/searchName")
    public Iterable<Product> getAllProducts(@RequestBody SearchRequestDto dto) {
        return elasticConsumerService.searchProduct(dto.getQuery(),"productName", dto.getSize(), dto.getPage());
    }

    @GetMapping("/searchStatus")
    public List<Product> searchProductByStatus(@RequestBody SearchRequestDto dto){
        return elasticConsumerService.searchProduct(dto.getQuery(),"status", dto.getSize(), dto.getPage());
    }




}






















