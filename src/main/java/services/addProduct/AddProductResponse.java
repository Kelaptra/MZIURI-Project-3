package services.addProduct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Product;

public class AddProductResponse {
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer remainingAmount;

    @JsonCreator
    public AddProductResponse(String name, Integer remainingAmount) {
        this.name = name;
        this.remainingAmount = remainingAmount;
    }

    public AddProductResponse(Product product) {
        this.name = product.getProd_name();
        this.remainingAmount = product.getProd_amount();
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
