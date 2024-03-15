package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Arrays;

public class StorageConfig {
    private Product[] products;
    private String password;

    @JsonCreator
    public StorageConfig(@JsonProperty("products") Product[] products,
                         @JsonProperty("password") String password) {
        this.products = products;
        this.password = password;
    }

    public Product[] getProducts() {
        return products;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "model.StorageConfig{" +
                "products=" + Arrays.toString(products) +
                ", password='" + password + '\'' +
                '}';
    }
}
