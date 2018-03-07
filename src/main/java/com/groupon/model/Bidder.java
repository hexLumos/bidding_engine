package com.groupon.model;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

//registered users can either be Bidders or BidOwners, here Bidder is an Observer to the Observable BidItem
//Bidder will be notified whenever a new bid is added on the item or when the item is sold
public class Bidder implements Observer {

    private final long userId;

    public Bidder(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bidder)) return false;
        Bidder bidder = (Bidder) o;
        return getUserId() == bidder.getUserId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserId());
    }

    @Override
    public String toString() {
        return "Bidder{" +
                "userId=" + userId +
                '}';
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            if ("BID_ADDED".equals(arg)) {
                System.out.println("Notification to UserId " + userId + " : bid has been added on " + o);
            } else if ("ITEM_SOLD".equals(arg)) {
                System.out.println("Notification to UserId " + userId + " : " + o + " has been sold");
            }
        }
    }
}
