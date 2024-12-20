package db.blindauction.auctionservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {
    @Autowired
    private RestTemplate restTemplate;

    public boolean isValidToken(String token) {
        String url = "http://localhost:8080/api/users/" + token;
        UserDto userDto = restTemplate.getForObject(url, UserDto.class);
        return userDto.getToken().compareTo(token)==0;
    }
}
