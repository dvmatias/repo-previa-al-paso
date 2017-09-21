package com.dvmatias.previaalpaso;

import java.util.ArrayList;

/**
 * Created by dvmatias on 21/09/17.
 */

public class Products {
    public int id;
    public String brand;
    public String name;
    public String type;
    public int content_ml;
    public String flavor;
    public int stock;
    public long price;

    /**
     * Empty constructor. Required!
     */
    public Products() {

    }

    /**
     * Class constructor. </br>
     *
     * @param id [int] Product id. Must be an int.
     * @param brand [String] Product brand name.
     * @param name [String] Product common name.
     * @param type [String] Product drink type.
     * @param content_ml [int] Product net content. Must be specified in ml.
     * @param flavor [String] Product flavor if any.
     * @param stock [int] Product quantity availability in stock.
     * @param price [long] Product cost and sale prices.
     */
    public Products(int id, String brand, String name, String type, int content_ml, String flavor, int stock, long price) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.type = type;
        this.content_ml = content_ml;
        this.flavor = flavor;
        this.stock = stock;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getContentMl() {
        return content_ml;
    }

    public void setContentMl(int content_ml) {
        this.content_ml = content_ml;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", content_ml=" + content_ml +
                ", flavor='" + flavor + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
