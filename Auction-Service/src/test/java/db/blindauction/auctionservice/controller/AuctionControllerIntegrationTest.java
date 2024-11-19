package db.blindauction.auctionservice.controller;

import db.blindauction.auctionservice.client.UserServiceClient;
import db.blindauction.auctionservice.model.Auction;
import db.blindauction.auctionservice.repository.AuctionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuctionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AuctionRepository auctionRepository;

    @MockBean
    private UserServiceClient userServiceClient; // Mock the UserServiceClient

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testRegisterAuctionIntegration() {
        // Mock the UserServiceClient response
        when(userServiceClient.isValidToken("token123")).thenReturn(true);

        String url = "http://localhost:" + port + "/api/auctions?sellerToken=token123&description=Sample Auction&minimumBid=100.0";

        ResponseEntity<Auction> response = restTemplate.postForEntity(url, null, Auction.class);

        assertNotNull(response.getBody());
        assertEquals("token123", response.getBody().getSellerToken());
        assertEquals("Sample Auction", response.getBody().getDescription());
        assertEquals(100.0, response.getBody().getMinimumBid(), 0.0001);

        // Verify UserServiceClient was called
        verify(userServiceClient, times(1)).isValidToken("token123");
    }

    @Test
    void testGetActiveAuctionsIntegration() {
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setDescription("Active Auction");
        auction.setSellerToken("token123");
        auction.setMinimumBid(100.0);
        auction.setActive(true);

        auctionRepository.save(auction);

        String url = "http://localhost:" + port + "/api/auctions/active";

        ResponseEntity<Auction[]> response = restTemplate.getForEntity(url, Auction[].class);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Active Auction", response.getBody()[0].getDescription());
    }
}
