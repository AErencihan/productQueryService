package com.example.productqueryservice.util;


import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.example.productqueryservice.dto.SearchRequestDto;
import lombok.experimental.UtilityClass;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;

import java.util.function.Supplier;


@UtilityClass
public class ESUtil {

    public static Query createMatchNameQuery(String query, String field, int size, int page) {
        Query queryBuilder = Query.of(q -> q.match(m -> m.field(field).query(query).boost(3.5F)));
        return new NativeQueryBuilder().withQuery(queryBuilder)
                .withPageable(Pageable.ofSize(size).withPage(page)).build().getQuery();

    }


    public static Supplier<Query> createBoolQuery(SearchRequestDto dto) {
        return () -> Query.of(q -> q.bool(boolQuery(dto.getQuery(), dto.getProductName(), dto.getSize(), dto.getPage())));

    }

    public static BoolQuery boolQuery(String query, String field, int size, int page) {
        return new BoolQuery.Builder()
                .filter(termQuery(query, field))
                .must(matchQuery(query, field))
                .build();

    }

    //termQuery belirli alanın tam olarak eşleşen kısmını döndürür
    public static Query termQuery(String query, String field) {
        return Query.of(q -> q.term(new TermQuery.Builder()
                .field(query)
                .value(field)
                .build()));

    }

    //matchQuery belirli alan içerisinde ki belirli bir diziyle eşleşen kısmı döndürür
    public static Query matchQuery(String query, String field) {
        return Query.of(q -> q.match(new MatchQuery.Builder()
                .field(query)
                .query(field)
                .build()));


    }




}
