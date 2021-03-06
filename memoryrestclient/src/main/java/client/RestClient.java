package client;

import interfaces.IRestClient;
import models.Player;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestClient implements IRestClient {
    private String restURL = "http://localhost:8096/memory/";

    private String usernameHeader = "username";
    private RestTemplate template;
    private HttpHeaders headers;

    public RestClient() {
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    public Object getPlayerByCredentials(String username, String password){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restURL + "usercred?")
            .queryParam(usernameHeader, username)
            .queryParam("password", password);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Player> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, Player.class);
        return response.getBody();
    }

    public boolean checkUsername(String username){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restURL + "checkusername?")
            .queryParam(usernameHeader, username);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, Boolean.class);
        return response.getBody();
    }

    public Object registerPlayer(String username, String password){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restURL + "adduser?")
            .queryParam(usernameHeader, username)
            .queryParam("password", password);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Player> response = template.exchange(builder.toUriString(), HttpMethod.POST, entity, Player.class);
        return response.getBody();
    }

    public void updatePlayer(Object updatedPlayer){
        Player player = (Player) updatedPlayer;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restURL + "user/{id}?")
            .queryParam("player", player)
            .queryParam("userid", player.getPlayerID());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        template.exchange(builder.toUriString(), HttpMethod.PUT, entity, Player.class);
    }
}
