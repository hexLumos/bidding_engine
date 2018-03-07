package com.groupon.exception;

public class ItemNotAvailableForBid extends Exception {

    public ItemNotAvailableForBid() {
        super("Item is not available to bid");
    }
}
