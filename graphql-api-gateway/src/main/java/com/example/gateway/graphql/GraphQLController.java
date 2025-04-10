package com.example.gateway.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;

import java.util.List;

@RestController
@RequestMapping("/graphql")
public class GraphQLController {

    @Autowired
    private GraphQLService graphQLService;

    @PostMapping
    public ResponseEntity<Object> executeGraphQL(@RequestBody String query) {
        return ResponseEntity.ok(graphQLService.executeQuery(query));
    }
}