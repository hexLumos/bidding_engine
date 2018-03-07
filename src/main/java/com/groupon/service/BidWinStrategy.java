package com.groupon.service;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.model.Bid;
import com.groupon.model.Bidder;

import java.util.List;

public interface BidWinStrategy {

    public Bidder findWinner(List<Bid> bidList);

    public List<Bidder> findTopKBidders(List<Bid> bidList, int k) throws InvalidSearchParameterException;
}
