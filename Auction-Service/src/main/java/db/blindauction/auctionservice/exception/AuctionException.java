package db.blindauction.auctionservice.exception;

/**
 * Custom exception for AuctionService to handle auction-related errors.
 */
public class AuctionException extends RuntimeException {
    public AuctionException(String message) {
        super(message);
    }
}
