package com.example.productqueryservice.api;

import com.example.productqueryservice.model.Product;
import com.example.productqueryservice.service.ElasticConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ElasticConsumerService elasticConsumerService;



    @GetMapping("/getAll/{query}")
    public Iterable<Product> getAllProducts(@PathVariable String query, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
        return elasticConsumerService.getALlProducts(query, size, page);
    }


}






















