package com.groupon.service;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.model.Bid;
import com.groupon.model.Bidder;

import java.util.*;

//returns the winner based on the maximun unique bids a bidder has placed against an item
public class MaxiumUniqueBidderStrategy implements BidWinStrategy {


    public Bidder findWinner(List<Bid> listOfBids) {
        if (listOfBids != null && listOfBids.size() >= 1) {
            List<Bid> bidList = new ArrayList<>(listOfBids);

            //create a map between the price and the list of bids
            Map<Double, List<Bid>> uniqueBidMap = new HashMap<>();
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

            //after finding out the count against each bid price, find out the number of bidders who placed unique bids
            Map<Bidder, Integer> uniqueBidderMap = new HashMap<>();
            for (Map.Entry<Double, List<Bid>> entry : uniqueBidMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().size() == 1) {
                    Bid bid = entry.getValue().get(0);
                    if (uniqueBidderMap.get(bid.getUser()) != null) {
                        uniqueBidderMap.put(bid.getUser(), uniqueBidderMap.get(bid.getUser()) + 1);
                    } else {
                        uniqueBidderMap.put(bid.getUser(), 1);
                    }
                }
            }
            //find out the bidder who placed the max number of unique bids
            int maxCount = 1;
            Bidder output = null;
            for (Map.Entry<Bidder, Integer> entry : uniqueBidderMap.entrySet()) {
                if (output == null) {
                    output = entry.getKey();
                }
                if (entry.getValue() != null && entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    output = entry.getKey();
                }
            }
            return output;

        }
        return null;
    }

    @Override
    public List<Bidder> findTopKBidders(List<Bid> listOfBids, int k) throws InvalidSearchParameterException {
        if (listOfBids != null && listOfBids.size() >= 1) {
            List<Bid> bidList = new ArrayList<>(listOfBids);

            //create a map between the price and the list of bids
            Map<Double, List<Bid>> uniqueBidMap = new HashMap<>();
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

            //after finding out the count against each bid price, find out the number of bidders who placed unique bids
            Map<Bidder, Integer> uniqueBidderMap = new HashMap<>();
            for (Map.Entry<Double, List<Bid>> entry : uniqueBidMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().size() == 1) {
                    Bid bid = entry.getValue().get(0);
                    if (uniqueBidderMap.get(bid.getUser()) != null) {
                        uniqueBidderMap.put(bid.getUser(), uniqueBidderMap.get(bid.getUser()) + 1);
                    } else {
                        uniqueBidderMap.put(bid.getUser(), 1);
                    }
                }
            }

            //create a tree map to basically find out the max count vs user mapping
            Map<Integer, Bidder> bidderTreeMap = new TreeMap<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if (o2 - o1 > 0)
                        return 1;
                    else if (o2 - o1 < 0)
                        return -1;
                    else
                        return 0;
                }
            });
            for (Map.Entry<Bidder, Integer> entry : uniqueBidderMap.entrySet()) {
                bidderTreeMap.put(entry.getValue(), entry.getKey());
            }

            //find the list of bidders, throw an exception is the list size != k
            List<Bidder> topBidders = new ArrayList<>();
            int i = 0;
            for (Map.Entry<Integer, Bidder> entry : bidderTreeMap.entrySet()) {
                if (i < k) {
                    topBidders.add(entry.getValue());
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
