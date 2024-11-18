package db.blindauction.auctionservice.repository;

import db.blindauction.auctionservice.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Long> {
}
