package com.dvmatias.previaalpaso.objects;

/**
 * Created by dvmatias on 22/09/17. Sponsor class that contains the infor of the unique
 * sponsor of a particular promotion.
 */

public class Sponsor {
    private String sponsor_name;
    private long sponsor_id;
    private long sponsor_discount;

    /**
     * Empty constructor.
     */
    public Sponsor() { }

    /**
     * Constructor. </br>
     *
     * @param sponsor_name [String] name of the unique sponsor.
     * @param sponsor_id [long] id of the sponsor.
     * @param sponsor_discount [long] sponsor discout.
     */
    public Sponsor(String sponsor_name, long sponsor_id, long sponsor_discount) {
        this.sponsor_name = sponsor_name;
        this.sponsor_id = sponsor_id;
        this.sponsor_discount = sponsor_discount;
    }

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    public long getSponsor_id() {
        return sponsor_id;
    }

    public void setSponsor_id(long sponsor_id) {
        this.sponsor_id = sponsor_id;
    }

    public long getSponsor_discount() {
        return sponsor_discount;
    }

    public void setSponsor_discount(long sponsor_discount) {
        this.sponsor_discount = sponsor_discount;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "sponsor_name='" + sponsor_name + '\'' +
                ", sponsor_id=" + sponsor_id +
                ", sponsor_discount=" + sponsor_discount +
                '}';
    }
}
