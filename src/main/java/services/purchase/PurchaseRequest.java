package services.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("amount")
    private Integer amount;

    @JsonCreator
    public PurchaseRequest(@JsonProperty("name")String name,
                           @JsonProperty("amount")Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }
}
