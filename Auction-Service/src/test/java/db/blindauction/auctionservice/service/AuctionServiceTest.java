package db.blindauction.auctionservice.service;

import db.blindauction.auctionservice.exception.AuctionException;
import db.blindauction.auctionservice.model.Auction;
import db.blindauction.auctionservice.model.Bid;
import db.blindauction.auctionservice.repository.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionService auctionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerAuction_shouldSaveAuction() {
        Auction auction = new Auction();
        auction.setSellerToken("seller1");
        auction.setDescription("Product A");
        auction.setMinimumBid(100.0);

        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);

        Auction result = auctionService.registerAuction("seller1", "Product A", 100.0);

        assertNotNull(result);
        assertEquals("seller1", result.getSellerToken());
        verify(auctionRepository, times(1)).save(any(Auction.class));
    }

    @Test
    void getAllAuctions_shouldReturnAllAuctions() {
        List<Auction> auctions = new ArrayList<>();
        auctions.add(new Auction());
        auctions.add(new Auction());
        when(auctionRepository.findAll()).thenReturn(auctions);
        List<Auction> result = auctionService.getAllAuctions();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(auctionRepository, times(1)).findAll();
    }

    @Test
    void placeBid_shouldAddBid() {
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setMinimumBid(100.0);
        auction.setActive(true);
        auction.setBids(new ArrayList<>());

        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);

        auctionService.placeBid(1L, "buyer1", 150.0);

        assertEquals(1, auction.getBids().size());
        assertEquals("buyer1", auction.getBids().get(0).getBuyerToken());
        verify(auctionRepository, times(1)).save(auction);
    }

    @Test
    void endAuction_shouldDetermineWinner() {
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setActive(true);
        Bid bid1 = new Bid();
        bid1.setAmount(100.0);
        Bid bid2 = new Bid();
        bid2.setAmount(150.0);
        auction.setBids(List.of(bid1, bid2));
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);

        Bid winner = auctionService.endAuction(1L);

        assertNotNull(winner);
        assertEquals(150.0, winner.getAmount());
        assertFalse(auction.isActive());
        verify(auctionRepository, times(1)).save(auction);
    }

    @Test
    void endAuction_shouldThrowErrorIfNoBids() {
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setActive(true);
        auction.setBids(new ArrayList<>());
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        Exception exception = assertThrows(AuctionException.class, () -> auctionService.endAuction(1L));
        assertEquals("No bids found for this auction!", exception.getMessage());
    }
}
