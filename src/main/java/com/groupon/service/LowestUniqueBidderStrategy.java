package com.groupon.service;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.model.Bid;
import com.groupon.model.Bidder;

import java.util.*;

//this strategy decide who's the winner based on the lowest unique bid placed on the item
public class LowestUniqueBidderStrategy implements BidWinStrategy {


    public Bidder findWinner(List<Bid> listOfBids) {
        if (listOfBids!=null && listOfBids.size()>=1) {
            List<Bid> bidList = new ArrayList<>(listOfBids);

            //maintains the map between the price and the list of bids, maintaining the natural order of the price
            Map<Double, List<Bid>> uniqueBidMap = new TreeMap<>();
            for (Bid bid : bidList) {
                if (uniqueBidMap.get(bid.getPrice()) == null) {
                    List<Bid> list = new ArrayList<>();
                    list.add(bid);
                    uniqueBidMap.put(bid.getPrice(), list);
                } else {
                    List<Bid> list = uniqueBidMap.get(bid.getPrice());
                    list.add(bid);
                    uniqueBidMap.put(bid.getPrice(), list);
                }
            }
            //return the first Bidder : while iterating over the entrySet, the first entry value whose size is 1
            for (Map.Entry<Double, List<Bid>> entry : uniqueBidMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().size() == 1)
                    return entry.getValue().get(0).getUser();
            }

        }
        return null;
    }

    @Override
    public List<Bidder> findTopKBidders(List<Bid> listOfBids, int k) throws InvalidSearchParameterException {
        if (listOfBids!=null && listOfBids.size()>=1) {
            List<Bid> bidList = new ArrayList<>(listOfBids);

            //maintains the map between the price and the list of bids, maintaining the natural order of the price
            Map<Double, List<Bid>> uniqueBidMap = new TreeMap<>();
            for (Bid bid : bidList) {
                if (uniqueBidMap.get(bid.getPrice()) == null) {
                    List<Bid> list = new ArrayList<>();
                    list.add(bid);
                    uniqueBidMap.put(bid.getPrice(), list);
                } else {
                    List<Bid> list = uniqueBidMap.get(bid.getPrice());
                    list.add(bid);
                    uniqueBidMap.put(bid.getPrice(), list);
                }
            }
            //while iterating over the entrySet, add the bidders (entry value) whose size is 1
            List<Bid> output = new ArrayList<>();
            for (Map.Entry<Double, List<Bid>> entry : uniqueBidMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().size() == 1){
                    output.add(entry.getValue().get(0));
                }
            }
            List<Bidder> topBidders = new ArrayList<>();
            int i = 0;
            for (Bid bid : output) {
                if (i < k) {
                    topBidders.add(bid.getUser());
                    i++;
                } else {
                    break;
                }
            }
            if (topBidders.size() == k) {
                return topBidders;
            } else {
                throw new InvalidSearchParameterException();
            }

        }
        return null;
    }

}
