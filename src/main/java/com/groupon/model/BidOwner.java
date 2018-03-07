package com.groupon.model;

public class BidOwner {

    private final long userId;

    private final long itemId;

    public BidOwner(long userId, long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public long getUserId() {
        return userId;
    }

    public long getItemId() {
        return itemId;
    }

    @Override
    public String toString() {
        return "BidOwner{" +
                "userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}
