package client;

import com.google.gson.Gson;
import interfaces.IRestClient;
import models.Player;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestClient implements IRestClient {
    private String restURL = "localhost:8096/memory/";

    private RestTemplate template;
    private Gson gson;

    public RestClient() {
        this.template = new RestTemplate();
        this.gson = new Gson();
    }

    public Object getPlayer(String username){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8096/memory/user?")
                .queryParam("username", username);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Player> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, Player.class);
        return response.getBody();
    }
}
