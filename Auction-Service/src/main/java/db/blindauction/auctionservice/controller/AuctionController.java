package db.blindauction.auctionservice.controller;

import db.blindauction.auctionservice.client.UserServiceClient;
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

    @Autowired
    private UserServiceClient userServiceClient;

    /**
     * Register a new auction with the seller token, description, and minimum bid.
     */
    @PostMapping
    public ResponseEntity<?> registerAuction(
            @RequestParam String sellerToken,
            @RequestParam String description,
            @RequestParam double minimumBid) {
        // Validate seller token
        if (!userServiceClient.isValidToken(sellerToken)) {
            return ResponseEntity.badRequest().body("Invalid seller token.");
        }
        // Validate minimum bid
        if (minimumBid < 0) {
            return ResponseEntity.badRequest().body("Minimum bid must be non-negative.");
        }
        Auction auction = auctionService.registerAuction(sellerToken, description, minimumBid);
        return ResponseEntity.ok(auction);
    }

    /**
     * List all active auctions.
     */
    @GetMapping("/active")
    public ResponseEntity<List<Auction>> getActiveAuctions() {
        List<Auction> activeAuctions = auctionService.getActiveAuctions();
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
        // Place bid
        try {
            auctionService.placeBid(auctionId, buyerToken, amount);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Bid placed successfully");
    }

    /**
     * End an auction and determine the winner.
     */
    @PostMapping("/{auctionId}/end")
    public ResponseEntity<?> endAuction(@PathVariable Long auctionId) {
        try {
            Bid winningBid = auctionService.endAuction(auctionId);
            return ResponseEntity.ok(winningBid);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
