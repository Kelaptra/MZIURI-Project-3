package services.addProduct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddProductRequest {
    @JsonProperty("password")
    private String password;
    @JsonProperty("name")
    private String name;
    @JsonProperty("amount")
    private Integer amount;

    @JsonCreator
    public AddProductRequest(@JsonProperty("password")String password,
                             @JsonProperty("name")String name,
                             @JsonProperty("amount")Integer amount) {
        this.password = password;
        this.name = name;
        this.amount = amount;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }
}
