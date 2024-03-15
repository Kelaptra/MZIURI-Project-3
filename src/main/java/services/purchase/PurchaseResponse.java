package services.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Product;

public class PurchaseResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("remainingAmount")
    private Integer remainingAmount;

    @JsonCreator
    public PurchaseResponse(Product product) {
        this.name = product.getProd_name();
        this.remainingAmount = product.getProd_amount();
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
