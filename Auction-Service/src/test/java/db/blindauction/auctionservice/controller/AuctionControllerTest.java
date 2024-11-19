package db.blindauction.auctionservice.controller;

import db.blindauction.auctionservice.model.Auction;
import db.blindauction.auctionservice.model.Bid;
import db.blindauction.auctionservice.service.AuctionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuctionController.class)
class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuctionService auctionService;

    @Test
    void testRegisterAuction() throws Exception {
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setDescription("Sample Auction");
        auction.setSellerToken("token123");
        auction.setMinimumBid(100.0);
        auction.setActive(true);

        when(auctionService.registerAuction(anyString(), anyString(), anyDouble())).thenReturn(auction);

        mockMvc.perform(post("/api/auctions")
                        .param("sellerToken", "token123")
                        .param("description", "Sample Auction")
                        .param("minimumBid", "100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Sample Auction"));
    }


    @Test
    void testGetActiveAuctions() throws Exception {
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setDescription("Active Auction");
        auction.setSellerToken("token123");
        auction.setMinimumBid(100.0);
        auction.setActive(true);

        when(auctionService.getActiveAuctions()).thenReturn(Collections.singletonList(auction));

        mockMvc.perform(get("/api/auctions/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("Active Auction"));
    }


    @Test
    void testPlaceBid() throws Exception {
        doNothing().when(auctionService).placeBid(anyLong(), anyString(), anyDouble());

        mockMvc.perform(post("/api/auctions/1/bid")
                        .param("buyerToken", "buyer123")
                        .param("amount", "150.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Bid placed successfully"));
    }

    @Test
    void testEndAuction() throws Exception {
        // Create a bid and set its values
        Bid winningBid = new Bid();
        winningBid.setBuyerToken("buyer123");
        winningBid.setAmount(200.0);
        // Mock the auction service to return the winning bid
        when(auctionService.endAuction(1L)).thenReturn(winningBid);

        // Perform the end auction API call and verify the response
        mockMvc.perform(post("/api/auctions/1/end"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyerToken").value("buyer123"))
                .andExpect(jsonPath("$.amount").value(200.0));
    }
}
