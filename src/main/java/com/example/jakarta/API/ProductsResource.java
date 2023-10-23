package com.example.jakarta;

import com.example.jakarta.entities.Product;
import com.example.jakarta.entities.ProductDto;
import com.example.jakarta.enums.categories;
import com.example.jakarta.interfaces.WarehouseInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("")
public class ProductsResource {


    WarehouseInterface warehouse;
    public ProductsResource () {}
    @Inject
    public ProductsResource(WarehouseInterface warehouse) {
        this.warehouse = warehouse;
    }

    @POST
    @Path("/addProduct")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addProduct(
            @FormParam("name") String name,
            @FormParam("category") String category) {

        // addProducts returns true if product is successfully added.
        return warehouse.addProduct(name, categories.valueOf(category))
                ? Response.status(Response.Status.CREATED).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> getAllProducts() {
        return warehouse.getAllProducts();
    }

    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") int id) {
        ProductDto product = warehouse.getProductById(id);

        if (product != null) {
            return Response.ok(product).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/productsFromDate/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> getProductsFromDate(@PathParam("date") String date) {
        LocalDateTime ldt = LocalDateTime.parse(date);
        return warehouse.getProductsFromDate(ldt);
    }

    @GET
    @Path("/modifiedProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> getModifiedProducts() {
        return warehouse.getModifiedProducts();
    }

    @GET
    @Path("/productsByCategory/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> getProductsByCategory(@PathParam("category") String category) {
        return warehouse.getProductsByCategory(category);
    }

    @GET
    @Path("/amountOfProductsByFirstLetter")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Character, Long> amountOfProductsByFirstLetter() {
        return warehouse.amountOfProductsByFirstLetter();
    }

    @GET
    @Path("/amountOfProductsByCategory/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public int amountOfProductsByCategory(@PathParam("category") String category) {
        return warehouse.amountOfProductsByCategory(category);
    }

    @GET
    @Path("/categoriesWithContent")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<categories, List<ProductDto>> getCategoriesWithContent() {
        return warehouse.getCategoriesWithContent();
    }

    @GET
    @Path("/topRatedThisMonth")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> getTopRatedThisMonth() {
        return warehouse.getTopRatedThisMonth();
    }
}