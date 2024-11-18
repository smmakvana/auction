package db.blindauction.auctionservice.repository;

import db.blindauction.auctionservice.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
    List<Auction> findByIsActiveTrue();
}
