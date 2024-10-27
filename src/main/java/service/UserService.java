package service;

import models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserService {
    private final RestTemplate restTemplate;
    private String sessionId;
    private final String baseUrl;

    public UserService(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    // session ID
    public void getSessionId() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + "/users", User[].class);
        HttpHeaders headers = response.getHeaders();
        this.sessionId = headers.getFirst("Set-Cookie");
    }

    // добавил пользователя
    public String createUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/users", request, String.class);
        return response.getBody(); // Вернуть тело ответа для получения кода
    }

    // измененил пользователя
    public String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/users", HttpMethod.PUT, request, String.class);
        return response.getBody(); // Вернуть тело ответа для получения кода
    }

    // удаленил пользователя
    public String deleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/users/" + userId, HttpMethod.DELETE, request, String.class);
        return response.getBody(); // Вернуть тело ответа для получения кода
    }
}
