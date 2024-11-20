# Auction Test
Create a prototype of a platform for running blind auctions.

## Blind Auction - Local Setup & Test

### Steps to Run on Local Machine:

1. **Start User Service:**
   - Navigate to the `user-service` directory.
   - Run the following command to start the User Service:
     ```bash
     ./mvnw spring-boot:run 
     or on window OS : mvn spring-boot:run 
     ```  
   - **Note:** The User Service has predefined users created during startup (`Alfa`, `Beta`, and `Gamma`) using the `@PostConstruct` method. These users are saved to the database when the service starts.

2. **Start Auction Service:**
   - Navigate to the `auction-service` directory.
   - Run the following command to start the Auction Service:
     ```bash
     ./mvnw spring-boot:run
     or on window OS : mvn spring-boot:run
     ```

3. **Run cURL Commands to Test the Application:**

   After both services are running, you can use cURL commands to test the application.  
   If you're on **Windows**, all the cURL commands listed below are included in the `run_auction.bat` file. Simply execute this file to run the commands sequentially:

    ```cmd
    run_auction.bat
    ```  

   Below are the individual cURL commands for reference:

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

   - **Place Bid (by `Beta` and `Gamma`):**
     ```bash
     curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token2&amount=150.0"  
     curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token3&amount=200.0"  
     curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token3&amount=99.0"  
     ```

   - **List Active Auctions:**
     ```bash
     curl http://localhost:8081/api/auctions/active  
     ```

   - **End Auction and Get Winning Bid:**
     ```bash
     curl -X POST http://localhost:8081/api/auctions/1/end  
     ```  

4. **Stop the Auction Service:**
   - Simply stop the `auction-service` and `user-service` applications via the terminal.

---

### Windows-Specific Note:
- The `run_auction.bat` file contains the commands above in a batch script format, allowing you to quickly test the application with a single execution.
- You can edit the `run_auction.bat` file if you need to modify or add commands.

This `README.md` provides a comprehensive overview of the necessary steps and commands to run and test the application locally.