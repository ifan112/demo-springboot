package com.ifan112.demo.springboot.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {

    @Override
    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
        return null;
    }
}
