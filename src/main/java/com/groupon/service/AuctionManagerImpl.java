package com.groupon.service;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.exception.ItemNotAvailableForBid;
import com.groupon.model.*;

import java.util.*;

public class AuctionManagerImpl implements AuctionManager {

    //BidMap contains the mapping between Item Id and the List of Bids that have been made against the item
    private Map<Long, List<Bid>> bidMap = new HashMap<Long, List<Bid>>();

    //BidOwner map contains the mapping between Item id and the BidOwner, this can be used in a payment system, in case we need to make the payment to the user who placed the item for auction
    private Map<Long, BidOwner> bidOwnerMap = new HashMap<>();

    //BidItemMap contains the mapping between Item id and the BidItem observable object
    private Map<Long, BidItem> bidItemMap = new HashMap<>();

    private BidWinStrategy bidWinStrategy;

    private final Object LOCK = new Object();

    public AuctionManagerImpl(BidWinStrategy bidWinStrategy) {
        this.bidWinStrategy = bidWinStrategy;
    }

    @Override
    public Bidder getTopBidder(Item item) {
        Bidder winningBidder = bidWinStrategy.findWinner(bidMap.get(item.getId()));

        //get the bidItem from the map and remove the winningBidder from the list of observers, and notify the other observers that the item is sold
        BidItem bidItem = bidItemMap.get(item.getId());
        bidItem.deleteObserver(winningBidder);
        bidItem.setMessage("ITEM_SOLD");

        return winningBidder;
    }

    @Override
    public void addItemForBid(Item item, BidOwner bidOwner) {
        //item is up for auction
        bidOwnerMap.put(item.getId(), bidOwner);
        bidMap.put(item.getId(), null);
    }

    public void addBid(Item item, Bidder user, double price) throws ItemNotAvailableForBid {
        synchronized (LOCK) {
            //if the item is not up for auction throw the ItemNotAvailableForBid exception
            if (!bidMap.containsKey(item.getId())) {
                throw new ItemNotAvailableForBid();
            }

            //create new bid object
            Bid bid = new Bid(price, user);

            //Add the bidItem to the map and notify other observers that a new bid has been added for the item
            BidItem bidItem = null;
            if (bidItemMap.get(item.getId()) != null) {
                bidItem = bidItemMap.get(item.getId());
                bidItem.setMessage("BID_ADDED");
                bidItem.addObserver(user);
            } else {
                bidItem = new BidItem(item.getId());
                bidItem.addObserver(user);
            }
            bidItemMap.put(item.getId(), bidItem);

            //add the bid against the item Id in the map
            if (bidMap.get(item.getId()) == null) {
                List<Bid> bidList = new ArrayList<>();
                bidList.add(bid);
                bidMap.put(item.getId(), bidList);
            } else {
                List<Bid> bidList = bidMap.get(item.getId());
                bidList.add(bid);
                bidMap.put(item.getId(), bidList);
            }
        }
    }

    /*@Override
    public void removeBid(Item item, Bidder user, double price) {
        synchronized (LOCK) {
            Bid bid = new Bid(price, user);
            if (bidMap.get(item) != null) {
                List<Bid> bidList = bidMap.get(item);
                bidList.remove(bid);
                bidMap.put(item.getId(), bidList);
            }
        }
    }*/

    public List<Bidder> topKBidders(Item item, int k) throws InvalidSearchParameterException {
        return bidWinStrategy.findTopKBidders(bidMap.get(item.getId()), k);
    }
}
