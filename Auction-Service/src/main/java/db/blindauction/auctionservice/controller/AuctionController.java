package db.blindauction.auctionservice.controller;

import db.blindauction.auctionservice.model.Auction;
import db.blindauction.auctionservice.model.Bid;
import db.blindauction.auctionservice.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    /**
     * Register a new auction with the seller token, description, and minimum bid.
     */
    @PostMapping
    public ResponseEntity<Auction> registerAuction(
            @RequestParam String sellerToken,
            @RequestParam String description,
            @RequestParam double minimumBid) {
        Auction auction = auctionService.registerAuction(sellerToken, description, minimumBid);
        return ResponseEntity.ok(auction);
    }

    /**
     * List all active auctions.
     */
    @GetMapping("/active")
    public ResponseEntity<List<Auction>> getActiveAuctions() {
        List<Auction> activeAuctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(activeAuctions);
    }

    /**
     * Place a bid on an auction.
     */
    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<String> placeBid(
            @PathVariable Long auctionId,
            @RequestParam String buyerToken,
            @RequestParam double amount) {
        auctionService.placeBid(auctionId, buyerToken, amount);
        return ResponseEntity.ok("Bid placed successfully");
    }

    /**
     * End an auction and determine the winner.
     */
    @PostMapping("/{auctionId}/end")
    public ResponseEntity<Bid> endAuction(@PathVariable Long auctionId) {
        Bid winningBid = auctionService.endAuction(auctionId);
        return ResponseEntity.ok(winningBid);
    }
}
