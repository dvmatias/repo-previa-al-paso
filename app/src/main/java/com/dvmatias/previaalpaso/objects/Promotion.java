package com.dvmatias.previaalpaso.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dvmatias on 22/09/17. Promotion class designed to create Promotion
 * objects.
 */

public class Promotion implements Parcelable{
    private String url_img;
    private String url_thumbnail;
    private String name;
    private ArrayList<Long> products_id;
    private long price;
    private double rating;
    private long votes_count;
    private String description;
    private String type;
    private Sponsor sponsor;
    private long id;
    private boolean inStock;
    private ArrayList<String> productsNames;

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
     * @param rating []
     * @param votes_count []
     * @param description []
     * @param type []
     * @param sponsor []
     * @param id []
     */
    public Promotion(String url_img, String url_thumbnail, String name, ArrayList<Long> products_id,
                     long price, double rating, long votes_count, String description, String type,
                     Sponsor sponsor, long id) {
        this.url_img = url_img;
        this.url_thumbnail = url_thumbnail;
        this.name = name;
        this.products_id = products_id;
        this.price = price;
        this.rating = rating;
        this.votes_count = votes_count;
        this.description = description;
        this.type = type;
        this.sponsor = sponsor;
        this.id = id;
    }

    public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel in) {
            return new Promotion(in);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getVotes_count() {
        return votes_count;
    }

    public void setVotes_count(long votes_count) {
        this.votes_count = votes_count;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public ArrayList<String> getProductsNames() {
        return productsNames;
    }

    public void setProductsNames(ArrayList<String> productsNames) {
        this.productsNames = productsNames;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "url_img='" + url_img + '\'' +
                ", url_thumbnail='" + url_thumbnail + '\'' +
                ", name='" + name + '\'' +
                ", products_id=" + products_id + '\'' +
                ", price=" + price + '\'' +
                ", rating=" + rating + '\'' +
                ", votes_count=" + votes_count + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", sponsor=" + sponsor + '\'' +
                ", id=" + id + '\'' +
                ", stock=" + inStock + '\'' +
                ", productsNames={" + productsNames + "}" +
                '}';
    }

    /*
     * Parcelling object.
     */
    public Promotion(Parcel in) {
        //retrieve
        this.url_img = in.readString();
        this.url_thumbnail = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.type = in.readString();
        this.price = in.readLong();
        this.votes_count = in.readLong();
        this.id = in.readLong();
        this.rating = in.readDouble();
        this.products_id = in.readArrayList(null);
        this.inStock = in.readByte() != 0;
        this.productsNames = in.readArrayList(null);
        // TODO: Sponsor not implemented.
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //write
        parcel.writeString(this.url_img);
        parcel.writeString(this.url_thumbnail);
        parcel.writeString(this.name);
        parcel.writeString(this.description);
        parcel.writeString(this.type);
        parcel.writeLong(this.price);
        parcel.writeLong(this.votes_count);
        parcel.writeLong(this.id);
        parcel.writeDouble(this.rating);
        parcel.writeList(this.products_id);
        parcel.writeByte((byte) (this.inStock ? 1 : 0));
        parcel.writeList(this.productsNames);
        // TODO: Sponsor not implemented.
    }

}
