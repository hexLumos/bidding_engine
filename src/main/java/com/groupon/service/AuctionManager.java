package com.groupon.service;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.exception.ItemNotAvailableForBid;
import com.groupon.model.BidOwner;
import com.groupon.model.Bidder;
import com.groupon.model.Item;

import java.util.List;

public interface AuctionManager {

    public Bidder getTopBidder(Item item);

    public void addItemForBid(Item item, BidOwner bidOwner);

    public void addBid(Item item, Bidder user, double price) throws ItemNotAvailableForBid;

    //public void removeBid(Item item, Bidder user, double price);

    public List<Bidder> topKBidders(Item item, int k) throws InvalidSearchParameterException;
}
