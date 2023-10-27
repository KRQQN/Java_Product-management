package com.example.jakarta.API;

import com.example.jakarta.entities.AddProductInput;
import com.example.jakarta.enums.Categories;
import com.example.jakarta.service.Warehouse;
import jakarta.inject.Inject;
import jakarta.validation.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {
    Warehouse warehouse;
    Validator validator;

    public ProductsResource () {}

    @Inject
    public ProductsResource(Warehouse warehouse, Validator validator) {
        this.warehouse = warehouse;
        this.validator = validator;
    }

    @POST
    @Path("/addProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid AddProductInput input) {

        return warehouse.addProduct(input.name(), Categories.valueOf(input.category()))
                ? Response.status(CREATED).build()
                : Response.status(BAD_REQUEST).build();
    }

    @GET
    @Path("/products")
    public Response getAllProducts() {
        return Response.ok(warehouse.getAllProducts()).build();
    }

    @GET
    @Path("/product/{id}")
    public Response getProductById(@PathParam("id") @PositiveOrZero int id) {
        return Response.ok(warehouse.getProductById(id)).build();
    }

    @GET
    @Path("/productsFromDate/{date}")
    public Response getProductsFromDate(@PathParam("date") @NotEmpty String date) {
        LocalDateTime ldt = LocalDateTime.parse(date);
        return Response.ok(warehouse.getProductsFromDate(ldt)).build();
    }

    @GET
    @Path("/modifiedProducts")
    public Response getModifiedProducts() {
        return Response.ok(warehouse.getModifiedProducts()).build();
    }

    @GET
    @Path("/productsByCategory/{category}")
    public Response getProductsByCategory(@PathParam("category") @NotEmpty String category) {
        return Response.ok(warehouse.getProductsByCategory(category)).build();
    }

    @GET
    @Path("/amountOfProductsByFirstLetter")
    public Response amountOfProductsByFirstLetter() {
        return Response.ok(warehouse.amountOfProductsByFirstLetter()).build();
    }

    @GET
    @Path("/amountOfProductsByCategory/{category}")
    public Response amountOfProductsByCategory(@PathParam("category") @NotEmpty String category) {
        return Response.ok(warehouse.amountOfProductsByCategory(category)).build();
    }

    @GET
    @Path("/categoriesWithContent")
    public Response getCategoriesWithContent() {
        return Response.ok(warehouse.getCategoriesWithContent()).build();
    }

    @GET
    @Path("/topRatedThisMonth")
    public Response getTopRatedThisMonth() {
        return Response.ok(warehouse.getTopRatedThisMonth()).build();
    }
}