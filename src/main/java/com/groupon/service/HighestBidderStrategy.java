package com.groupon.service;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.model.Bid;
import com.groupon.model.Bidder;

import java.util.*;

//this strategy decide who is the winner based on the highest bid placed on the item
public class HighestBidderStrategy implements BidWinStrategy {


    public Bidder findWinner(List<Bid> listOfBids) {
        if (listOfBids != null && listOfBids.size() >= 1) {
            List<Bid> bidList = new ArrayList<>(listOfBids);
            Bid maxBid = bidList.get(0);
            for (Bid bid : bidList) {
                if (bid.getPrice() > maxBid.getPrice()) {
                    maxBid = bid;
                }
            }
            return maxBid.getUser();
        }
        return null;
    }

    @Override
    public List<Bidder> findTopKBidders(List<Bid> listOfBids, int k) throws InvalidSearchParameterException {
        if (listOfBids != null && listOfBids.size() >= 1) {
            List<Bid> bidList = new ArrayList<>(listOfBids);

            //sort the bid list on decreasing order of prices
            Collections.sort(bidList, new Comparator<Bid>() {
                @Override
                public int compare(Bid o1, Bid o2) {
                    if (o2.getPrice() > o1.getPrice())
                        return 1;
                    else if (o2.getPrice() < o1.getPrice())
                        return -1;
                    else
                        return 0;
                }
            });
            List<Bidder> topBidders = new ArrayList<>();
            int i = 0;
            for (Bid bid : bidList) {
                if (i < k) {
                    topBidders.add(bid.getUser());
                    i++;
                } else {
                    break;
                }
            }

            //return the top K bidders only if there are k bidders, else throw an Exception
            if (topBidders.size() == k) {
                return topBidders;
            } else {
                throw new InvalidSearchParameterException();
            }
        }
        return null;
    }

}
