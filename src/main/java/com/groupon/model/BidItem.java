package com.groupon.model;

import java.util.Observable;

//BidItem is the observable which will be adding a list of observers whenever a new bid is added on an item
public class BidItem extends Observable {

    private long itemId;

    private String message;

    public BidItem(long itemId) {
        this.itemId = itemId;
    }

    public void setMessage(String message) {
        this.message = message;
        setChanged();
        notifyObservers(message);
    }

    @Override
    public String toString() {
        return "BidItem{" +
                "itemId=" + itemId +
                '}';
    }
}
