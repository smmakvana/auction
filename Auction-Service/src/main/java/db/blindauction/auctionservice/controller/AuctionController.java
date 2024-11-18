package db.blindauction.auctionservice.controller;

import db.blindauction.auctionservice.model.Auction;
import db.blindauction.auctionservice.model.Bid;
import db.blindauction.auctionservice.repository.AuctionRepository;
import db.blindauction.auctionservice.repository.BidRepository;
import db.blindauction.auctionservice.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @GetMapping
    public List<Auction> getActiveAuctions(){
        return auctionRepository.findByIsActiveTrue();
    }

    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        if (!userServiceClient.isValidToken(auction.getSellerToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(auctionRepository.save(auction));
    }

    @PostMapping("/{auctionId}/bids")
    public ResponseEntity<String> placeBid(@PathVariable Long auctionId, @RequestBody Bid bid) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));
        if (!userServiceClient.isValidToken(bid.getBuyerToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (bid.getAmount() < auction.getMinimumBid()) {
            return ResponseEntity.badRequest().body("Bid is below the minimum price");
        }
        bid.setAuction(auction);
        bidRepository.save(bid);
        return ResponseEntity.ok("Bid placed successfully");
    }

    @PostMapping("/{auctionId}/end")
    public ResponseEntity<Bid> endAuction(@PathVariable Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));
        auction.setActive(false);
        auctionRepository.save(auction);

        final Bid winningBid = auction.getBids().stream()
                .max(Comparator.comparingDouble(Bid::getAmount)
                        .thenComparing(Bid::getTimestamp))
                .orElse(null);
        return ResponseEntity.ok(winningBid);
    }

}
