package com.riseroots;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    Logger logger;

    @GET
    public List<Customer> getAllCustomers() {
        return Customer.listAll();
    }

    @POST
    @Transactional
    public Response addCustomer(Customer customer) {
        customer.persist();
        logger.info("Customer added: " + customer.customerName);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }
}
