package com.ifan112.demo.springboot.graphql;

import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DemoGraphqlService {

    @Value("classpath:schema.graphql")
    private Resource resource;

    @Autowired
    private GraphQL graphQL;


}
