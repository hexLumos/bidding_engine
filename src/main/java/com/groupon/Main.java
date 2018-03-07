package com.groupon;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.exception.ItemNotAvailableForBid;
import com.groupon.model.BidOwner;
import com.groupon.model.Bidder;
import com.groupon.model.Item;
import com.groupon.model.User;
import com.groupon.service.*;

public class Main {
    public static void main(String[] args) throws ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new MaxiumUniqueBidderStrategy());

        Item item = new Item(1,"SamsungPhone","phone");
        User user1 = new User("A",1);
        User user2 = new User("B",2);
        User user3 = new User("C",3);
        User user4 = new User("D",4);


        BidOwner bidOwner = new BidOwner(user4.getId(),item.getId());
        Bidder bidder1 = new Bidder(user1.getId());
        Bidder bidder2 = new Bidder(user2.getId());
        Bidder bidder3 = new Bidder(user3.getId());

        auctionManager.addItemForBid(item,bidOwner);
        auctionManager.addBid(item,bidder1,100);
        auctionManager.addBid(item,bidder2,100);
        auctionManager.addBid(item,bidder1,200);
        auctionManager.addBid(item,bidder1,500);
        auctionManager.addBid(item,bidder2,10000);
        auctionManager.addBid(item,bidder3,800);
        auctionManager.addBid(item,bidder3,900);
        auctionManager.addBid(item,bidder3,700);
        auctionManager.addBid(item,bidder3,600);


        try {
            System.out.println(auctionManager.topKBidders(item,2));
        } catch (InvalidSearchParameterException e) {
            e.printStackTrace();
        }
        System.out.println(auctionManager.getTopBidder(item));


    }
}
