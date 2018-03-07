package com.groupon.service;

import com.groupon.exception.InvalidSearchParameterException;
import com.groupon.exception.ItemNotAvailableForBid;
import com.groupon.model.BidOwner;
import com.groupon.model.Bidder;
import com.groupon.model.Item;
import com.groupon.model.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuctionManagerTest {

    @Test
    public void testAddItemForBid_OK() throws ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new HighestBidderStrategy());

        Item item = new Item(1, "SamsungPhone", "phone");
        User user1 = new User("A", 1);
        User user4 = new User("D", 4);

        BidOwner bidOwner = new BidOwner(user4.getId(), item.getId());
        Bidder bidder1 = new Bidder(user1.getId());
        auctionManager.addItemForBid(item, bidOwner);
        auctionManager.addBid(item, bidder1, 100);
    }

    @Test(expected = ItemNotAvailableForBid.class)
    public void testAddItemForBid_Exception() throws ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new HighestBidderStrategy());

        Item item = new Item(1, "SamsungPhone", "phone");
        User user1 = new User("A", 1);

        Bidder bidder1 = new Bidder(user1.getId());
        auctionManager.addBid(item, bidder1, 100);
    }

    @Test
    public void testHighestBidderStrategy() throws ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new HighestBidderStrategy());

        Item item = new Item(1, "SamsungPhone", "phone");
        User user1 = new User("A", 1);
        User user2 = new User("B", 2);
        User user3 = new User("C", 3);
        User user4 = new User("D", 4);

        BidOwner bidOwner = new BidOwner(user4.getId(), item.getId());
        Bidder bidder1 = new Bidder(user1.getId());
        Bidder bidder2 = new Bidder(user2.getId());
        Bidder bidder3 = new Bidder(user3.getId());

        auctionManager.addItemForBid(item, bidOwner);
        auctionManager.addBid(item, bidder1, 100);
        auctionManager.addBid(item, bidder2, 100);
        auctionManager.addBid(item, bidder1, 200);
        auctionManager.addBid(item, bidder1, 500);
        auctionManager.addBid(item, bidder2, 10000);
        auctionManager.addBid(item, bidder3, 800);
        auctionManager.addBid(item, bidder3, 900);
        auctionManager.addBid(item, bidder3, 700);
        auctionManager.addBid(item, bidder3, 600);

        assertEquals(bidder2, auctionManager.getTopBidder(item));
    }

    @Test
    public void testLowestUniqueBidderStrategy() throws ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new LowestUniqueBidderStrategy());

        Item item = new Item(1, "SamsungPhone", "phone");
        User user1 = new User("A", 1);
        User user2 = new User("B", 2);
        User user3 = new User("C", 3);
        User user4 = new User("D", 4);

        BidOwner bidOwner = new BidOwner(user4.getId(), item.getId());
        Bidder bidder1 = new Bidder(user1.getId());
        Bidder bidder2 = new Bidder(user2.getId());
        Bidder bidder3 = new Bidder(user3.getId());

        auctionManager.addItemForBid(item, bidOwner);
        auctionManager.addBid(item, bidder1, 100);
        auctionManager.addBid(item, bidder2, 100);
        auctionManager.addBid(item, bidder1, 200);
        auctionManager.addBid(item, bidder1, 500);
        auctionManager.addBid(item, bidder2, 10000);
        auctionManager.addBid(item, bidder3, 800);
        auctionManager.addBid(item, bidder3, 900);
        auctionManager.addBid(item, bidder3, 700);
        auctionManager.addBid(item, bidder3, 600);

        assertEquals(bidder1, auctionManager.getTopBidder(item));
    }

    @Test
    public void testMaximumUniqueBidderStrategy() throws ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new MaxiumUniqueBidderStrategy());

        Item item = new Item(1, "SamsungPhone", "phone");
        User user1 = new User("A", 1);
        User user2 = new User("B", 2);
        User user3 = new User("C", 3);
        User user4 = new User("D", 4);

        BidOwner bidOwner = new BidOwner(user4.getId(), item.getId());
        Bidder bidder1 = new Bidder(user1.getId());
        Bidder bidder2 = new Bidder(user2.getId());
        Bidder bidder3 = new Bidder(user3.getId());

        auctionManager.addItemForBid(item, bidOwner);
        auctionManager.addBid(item, bidder1, 100);
        auctionManager.addBid(item, bidder2, 100);
        auctionManager.addBid(item, bidder1, 200);
        auctionManager.addBid(item, bidder1, 500);
        auctionManager.addBid(item, bidder2, 10000);
        auctionManager.addBid(item, bidder3, 800);
        auctionManager.addBid(item, bidder3, 900);
        auctionManager.addBid(item, bidder3, 700);
        auctionManager.addBid(item, bidder3, 600);

        assertEquals(bidder3, auctionManager.getTopBidder(item));
    }

    @Test
    public void testHighestBidderTopKStrategy() throws InvalidSearchParameterException, ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new HighestBidderStrategy());

        Item item = new Item(1, "SamsungPhone", "phone");
        User user1 = new User("A", 1);
        User user2 = new User("B", 2);
        User user3 = new User("C", 3);
        User user4 = new User("D", 4);

        BidOwner bidOwner = new BidOwner(user4.getId(), item.getId());
        Bidder bidder1 = new Bidder(user1.getId());
        Bidder bidder2 = new Bidder(user2.getId());
        Bidder bidder3 = new Bidder(user3.getId());

        auctionManager.addItemForBid(item, bidOwner);
        auctionManager.addBid(item, bidder1, 100);
        auctionManager.addBid(item, bidder2, 100);
        auctionManager.addBid(item, bidder1, 200);
        auctionManager.addBid(item, bidder1, 500);
        auctionManager.addBid(item, bidder2, 10000);
        auctionManager.addBid(item, bidder3, 800);
        auctionManager.addBid(item, bidder3, 900);
        auctionManager.addBid(item, bidder3, 700);
        auctionManager.addBid(item, bidder3, 600);

        List<Bidder> output = auctionManager.topKBidders(item, 4);
        assertEquals(bidder2, output.get(0));
        assertEquals(bidder3, output.get(1));
        assertEquals(bidder3, output.get(2));
        assertEquals(bidder3, output.get(3));
    }

    @Test(expected = InvalidSearchParameterException.class)
    public void testHighestBidderTopKStrategyWithException() throws InvalidSearchParameterException, ItemNotAvailableForBid {
        AuctionManager auctionManager = new AuctionManagerImpl(new HighestBidderStrategy());

        Item item = new Item(1, "SamsungPhone", "phone");
        User user1 = new User("A", 1);
        User user2 = new User("B", 2);
        User user3 = new User("C", 3);
        User user4 = new User("D", 4);

        BidOwner bidOwner = new BidOwner(user4.getId(), item.getId());
        Bidder bidder1 = new Bidder(user1.getId());
        Bidder bidder2 = new Bidder(user2.getId());
        Bidder bidder3 = new Bidder(user3.getId());

        auctionManager.addItemForBid(item, bidOwner);
        auctionManager.addBid(item, bidder1, 100);
        auctionManager.addBid(item, bidder2, 100);
        auctionManager.addBid(item, bidder1, 200);
        auctionManager.addBid(item, bidder1, 500);
        auctionManager.addBid(item, bidder2, 10000);
        auctionManager.addBid(item, bidder3, 800);
        auctionManager.addBid(item, bidder3, 900);
        auctionManager.addBid(item, bidder3, 700);
        auctionManager.addBid(item, bidder3, 600);

        List<Bidder> output = auctionManager.topKBidders(item, 10);

    }


}
