package com.dvmatias.previaalpaso.objects;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dvmatias on 22/09/17. Promotion class designed to create Promotion
 * objects.
 */

public class Promotion {
    private String url_img;
    private String name;
    private ArrayList<Long> products_id;
    private long price;
    private String description;
    private String type;
    private Sponsor sponsor;

    /**
     * Empty constructor.
     */
    public Promotion() { }

    /**
     * Constructor. </br>
     *
     * @param url_img [String] Image url.
     * @param name []
     * @param products_id []
     * @param price []
     * @param description []
     * @param type []
     * @param sponsor []
     */
    public Promotion(String url_img, String name, ArrayList<Long> products_id, long price, String description, String type, Sponsor sponsor) {
        this.url_img = url_img;
        this.name = name;
        this.products_id = products_id;
        this.price = price;
        this.description = description;
        this.type = type;
        this.sponsor = sponsor;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Long> getProducts_id() {
        return products_id;
    }

    public void setProducts_id(ArrayList<Long> products_id) {
        this.products_id = products_id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "url_img='" + url_img + '\'' +
                ", name='" + name + '\'' +
                ", products_id=" + products_id +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", sponsor=" + sponsor.toString() +
                '}';
    }
}
