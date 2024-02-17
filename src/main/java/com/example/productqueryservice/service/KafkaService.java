package com.example.productqueryservice.service;


import com.example.productqueryservice.model.Product;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaService {

    public Object deSerializedData(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object obj = null;

        try {
            obj = objectMapper.readValue(str, Product.class);

        } catch (JsonParseException e) {
            e.printStackTrace();

        } catch (JsonMappingException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }


    @KafkaListener(topics = "topicProduct", groupId = "group_id")
    public void consume(String message) {
        Object object = deSerializedData(message);
        System.out.println(object);


    }


}



















