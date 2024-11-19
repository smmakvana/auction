package db.blindauction.auctionservice.service;

import db.blindauction.auctionservice.exception.AuctionException;
import db.blindauction.auctionservice.model.Auction;
import db.blindauction.auctionservice.model.Bid;
import db.blindauction.auctionservice.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    /**
     * Register a new auction with a seller, product, and minimum bid.
     */
    public Auction registerAuction(String sellerToken, String description, double minimumBid) {
        Auction auction = new Auction();
        auction.setSellerToken(sellerToken);
        auction.setDescription(description);
        auction.setMinimumBid(minimumBid);
        auction.setActive(true); // Auction is active by default
        return auctionRepository.save(auction);
    }

    /**
     * List all auctions.
     */
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    /**
     * Place a bid for a specific auction.
     */
    public void placeBid(Long auctionId, String buyerToken, double amount) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionException("Auction not found with ID: " + auctionId));

        if (!auction.isActive()) {
            throw new AuctionException("Auction is no longer active!");
        }

        if (amount < auction.getMinimumBid()) {
            throw new AuctionException("Bid amount is less than the minimum bid!");
        }

        Bid bid = new Bid();
        bid.setBuyerToken(buyerToken);
        bid.setAmount(amount);
        auction.getBids().add(bid);
        auctionRepository.save(auction);
    }

    /**
     * End the auction and determine the winner.
     */
    public Bid endAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionException("Auction not found with ID: " + auctionId));

        if (!auction.isActive()) {
            throw new AuctionException("Auction is already closed!");
        }

        if (auction.getBids().isEmpty()) {
            throw new AuctionException("No bids found for this auction!");
        }

        // Get the highest bid
        Bid winningBid = auction.getBids()
                .stream()
                .max((bid1, bid2) -> Double.compare(bid1.getAmount(), bid2.getAmount()))
                .orElseThrow(() -> new AuctionException("Unable to determine the winning bid"));

        // Close the auction
        auction.setActive(false);
        auctionRepository.save(auction);
        return winningBid;
    }
}
