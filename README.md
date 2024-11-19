# Auction Test
Create a prototype of a platform for running blind  auctions
# Blind Auction - Local Setup & Test

### Steps to Run on Local Machine:

1. **Start User Service:**
    - Navigate to the `user-service` directory.
    - Run the following command to start the User Service:
      ```bash
      ./mvnw spring-boot:run
      ```
    - **Note:** The User Service has predefined users created during startup (`Alfa`, `Beta`, and `Gamma`) using the `@PostConstruct` method. These users are saved to the database when the service starts.

2. **Start Auction Service:**
    - Navigate to the `auction-service` directory.
    - Run the following command to start the Auction Service:
      ```bash
      ./mvnw spring-boot:run
      ```

3. **Test using cURL Commands:**

   After both services are running, use the following `curl` commands to test the application.

    - **Create Users:**
      _Note: Since the User Service has predefined users (`Alfa`, `Beta`, and `Gamma`), this step is not necessary, but can be done to create new users manually if needed._
      ```bash
      curl -X POST http://localhost:8081/api/users/token1 // will return user data
      curl -X POST http://localhost:8081/api/users/token2 // will return user data
      curl -X POST http://localhost:8081/api/users/token3 // will return user data
      curl -X POST http://localhost:8081/api/users/token4 // will return error response
      ```

    - **Create Auction (by `Alfa`):**
      ```bash
      curl -X POST "http://localhost:8081/api/auctions?description=Sample+Auction1&sellerToken=token1&minimumBid=100.0"
      curl -X POST "http://localhost:8081/api/auctions?description=Sample+Auction2&sellerToken=token1&minimumBid=200.0"
      ```

    - **Place Bid (by `Beta` and `Gemma`):**
      ```bash
      curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token2&amount=150.0"
      curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token3&amount=200.0"
      ```

    - **List Active Auctions:**
      ```bash
      curl http://localhost:8081/api/auctions/active
      ```

    - **End Auction and Get Winning Bid:**
      ```bash
      curl -X POST http://localhost:8081/api/auctions/1/end
      ```

### Stop the Auction Service:
- Simply stop the `auction-service` and `user-service` applications via the terminal.

---

This `README.md` provides a minimal overview of the necessary commands to run and test your application locally, with predefined users (`Alfa`, `Beta`, and `Gamma`) already set up in the User Service.
