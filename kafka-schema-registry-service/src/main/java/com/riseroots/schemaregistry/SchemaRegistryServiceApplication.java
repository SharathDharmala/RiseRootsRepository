package com.riseroots.schemaregistry;

import java.util.Collection;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@RequestMapping("/schemas")
@RequiredArgsConstructor
public class SchemaRegistryServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(SchemaRegistryServiceApplication.class);
    
    private final SchemaRegistryClient schemaRegistryClient;  // Injected via constructor

    public static void main(String[] args) {
        SpringApplication.run(SchemaRegistryServiceApplication.class, args);
    }

    @PostMapping("/register/{subject}")
    public int registerSchema(@PathVariable String subject, @RequestBody String schemaString) throws Exception {
        Schema schema = new Schema.Parser().parse(schemaString);
        return schemaRegistryClient.register(subject, schema);  // Use the injected field
    }

    @GetMapping("/get/{subject}/latest")
    public String getLatestSchema(@PathVariable String subject) throws Exception {
        String schema = schemaRegistryClient.getLatestSchemaMetadata(subject).getSchema();
        logger.debug("Schema Registry Subjects:: {}", schema);
        return schema;
    }

    @GetMapping("/subjects")
    public Collection<String> getSubjects() throws Exception {
        logger.debug("Schema Registry Subjects:: {}", schemaRegistryClient.getAllSubjects());
        return schemaRegistryClient.getAllSubjects();
    }
}

