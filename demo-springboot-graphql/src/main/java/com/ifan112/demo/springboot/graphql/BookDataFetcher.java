package com.ifan112.demo.springboot.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;

@Service
public class BookDataFetcher  implements DataFetcher<Book> {

    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
        return null;
    }
}
