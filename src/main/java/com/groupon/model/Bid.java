package com.groupon.model;

import java.util.Objects;

public class Bid {

    private double price;
    private Bidder user;

    public Bid(double price, Bidder user) {
        this.price = price;
        this.user = user;
    }


    public double getPrice() {
        return price;
    }

    public Bidder getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid)) return false;
        Bid bid = (Bid) o;
        return Double.compare(bid.getPrice(), getPrice()) == 0 &&
                Objects.equals(getUser(), bid.getUser());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPrice(), getUser());
    }

    @Override
    public String toString() {
        return "Bid{" +
                "price=" + price +
                ", user=" + user +
                '}';
    }
}
