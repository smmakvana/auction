echo Running cURL tests...

echo Checking Users...
curl -X POST http://localhost:8080/api/users/token1
curl -X POST http://localhost:8080/api/users/token2
curl -X POST http://localhost:8080/api/users/token3
curl -X POST http://localhost:8080/api/users/token4

echo Listing Active Auctions...
curl http://localhost:8081/api/auctions/active
echo Creating Auctions...
curl -X POST "http://localhost:8081/api/auctions?description=Car&sellerToken=token1&minimumBid=100.0"
echo Listing Active Auctions...
curl http://localhost:8081/api/auctions/active
echo Creating Auctions...
curl -X POST "http://localhost:8081/api/auctions?description=Mobile&sellerToken=token1&minimumBid=200.0"
echo Listing Active Auctions...
curl http://localhost:8081/api/auctions/active

echo Placing Bids...
curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token2&amount=150.0"
curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token3&amount=200.0"
curl -X POST "http://localhost:8081/api/auctions/1/bid?buyerToken=token3&amount=99.0"

echo Listing Active Auctions...
curl http://localhost:8081/api/auctions/active

echo Ending Auction and Retrieving Winning Bid...
curl -X POST http://localhost:8081/api/auctions/1/end

echo Listing Active Auctions...
curl http://localhost:8081/api/auctions/active

echo Auction Test Completed!
echo Please check the output in the respective services for more details.
pause