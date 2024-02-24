package com.example.productqueryservice.util;


import lombok.experimental.UtilityClass;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;




@UtilityClass
public class ESUtil {

    public static Query createMatchNameQuery(String query, String field, int size, int page) {
        Query queryBuilder = Query.of(q -> q.match(m -> m.field(field).query(query).boost(3.5F)));
        return new NativeQueryBuilder().withQuery(queryBuilder)
                .withPageable(Pageable.ofSize(size).withPage(page)).build().getQuery();

    }











}
