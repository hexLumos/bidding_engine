# bidding_engine

POJO/Model objects :

- Item
- User
- Bidder and BidOwner ( a registered user can be either a bidder or a bidOwner)
- BidItem

Services exposed (Auction Manager interface) :

● Users should be able to put an item for auction

- addItemForBid(Item item, BidOwner bidOwner)

● Users can place bids on an item;multiple bids are allowed on a single item from same user also.

- addBid(Item item, Bidder user, double price)

● Users could choose an item and view its top k bidders[by winning strategy]

- topKBidders(Item item, int k)

● Bidders are notified when there is a new bid is placed on an item and All Bidders are notified when the item is sold to the winner.

- To notify whenever a new bid is added or the item is sold, I've made the BidItem as Observable while Bidder as an Observer.

Strategies implemented - Highest Bidder, Lowest Unique Bidder and Maximum Unique Bids which are implementations of the BidWinStrategy
