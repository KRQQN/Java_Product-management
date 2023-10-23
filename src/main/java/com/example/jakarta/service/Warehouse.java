package com.example.jakarta.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import com.example.jakarta.entities.Product;
import com.example.jakarta.entities.ProductDto;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.example.jakarta.enums.Categories;

import static java.util.stream.Collectors.counting;
import static com.example.jakarta.entities.ProductDto.objToRecord;

@ApplicationScoped
public class Warehouse  {
    private final List<Product> products;
    Predicate<String> validStrLength = value -> value.length() >= 2;
    Predicate<Integer> validRating_0_10 = rating -> rating >= 0 && rating <= 10;

    public Warehouse() {
        this.products = new CopyOnWriteArrayList<>();
    }

    public boolean addProduct(@NotEmpty String name, Categories category) {
        boolean res = validStrLength.test(name);

        if (res) products.add(new Product(name, category));
        return res;
    }

    @TestOnly
    public boolean addProduct(int id, @NotEmpty String name,Categories category, Clock mockClock) {
        boolean res = validStrLength.test(name);

        if (res) products.add(new Product(id, name, category, mockClock));
        return res;
    }

    @TestOnly
    public boolean addProduct(int id, @NotEmpty String name,Categories category) {
        boolean res = validStrLength.test(name);

        if (res && findProductById(id).isEmpty()) products.add(new Product(id, name, category));
        return res;
    }

    public List<ProductDto> getAllProducts() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .toList();
    }

    public boolean editProduct(int id, String name, Categories category, int rating) {
        Optional<Product> res = findProductById(id);
        boolean validName = validStrLength.test(name);
        boolean validRating = validRating_0_10.test(rating);

        if (validName && validRating && res.isPresent()) {
            res.ifPresent(product -> {
                product.setCategory(category)
                        .setRating(rating)
                        .setName(name)
                        .setModified();
            });
            return true;
        }
        return false;
    }

    public ProductDto getProductById(int id) {
        return objToRecord(findProductById(id));
    }

    public List<ProductDto> getProductsFromDate(LocalDateTime ldt) {
        return products.stream()
                .filter(p -> ldt.isBefore(p.getCreated()))
                .map(ProductDto::objToRecord)
                .toList();
    }

    public List<ProductDto> getModifiedProducts() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .filter(product -> product.modified().isAfter(product.created()))
                .toList();
    }

    public List<ProductDto> getProductsByCategory(String categoryToFind) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .filter(prod -> prod.getCategory().equals(Categories.valueOf(categoryToFind)))
                .map(ProductDto::objToRecord)
                .toList();
    }

    public Map<Character, Long> amountOfProductsByFirstLetter() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.getName().toUpperCase().charAt(0), counting()
                ));
    }

    public int amountOfProductsByCategory(String category) {
        return getProductsByCategory(category).size();
    }

    public Map<Categories, List<ProductDto>> getCategoriesWithContent() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .collect(Collectors.groupingBy(ProductDto::category));
    }

    public List<ProductDto> getTopRatedThisMonth() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .filter(product -> YearMonth.from(product.created()).equals(YearMonth.now()))
                .filter(product -> product.rating() == 10)
                .sorted(Comparator.comparing(ProductDto::created))
                .toList();
    }

    private @NotNull Optional<Product> findProductById(int id) {
        return products.stream()
                .filter(obj -> obj.getId() == id)
                .findFirst();
    }
}
