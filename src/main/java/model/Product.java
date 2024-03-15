package model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private int prod_id;
    @Column(name = "prod_name")
    private String prod_name;
    @Column(name = "prod_price")
    private float prod_price;
    @Column(name = "prod_amount")
    private int prod_amount;

    public Product(String prod_name, float prod_price, int prod_amount) {
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_amount = prod_amount;
    }
    public Product(){}

    public void changeAmountBy(Integer amount){
        prod_amount += amount;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public float getProd_price() {
        return prod_price;
    }

    public void setProd_price(float prod_price) {
        this.prod_price = prod_price;
    }

    public int getProd_amount() {
        return prod_amount;
    }

    public void setProd_amount(int prod_amount) {
        this.prod_amount = prod_amount;
    }

    @Override
    public String toString() {
        return "com.mziuri.Application.Product{" +
                "prod_id=" + prod_id +
                ", prod_name='" + prod_name + '\'' +
                ", prod_price=" + prod_price +
                ", prod_amount=" + prod_amount +
                '}';
    }
}