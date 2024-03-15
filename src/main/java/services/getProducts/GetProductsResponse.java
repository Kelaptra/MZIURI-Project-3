package services.getProducts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GetProductsResponse {
    @JsonProperty
    private String[] productNames;


    public void setProductNames(String[] names) {
        this.productNames = names;
    }



    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
