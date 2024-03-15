package services.getProducts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Product;

public class GetProductInfoResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("amount")
    private Integer amount;

    @JsonCreator
    public GetProductInfoResponse(String name, Float price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public GetProductInfoResponse(Product product) {
        this.name = product.getProd_name();
        this.price = product.getProd_price();
        this.amount = product.getProd_amount();
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
