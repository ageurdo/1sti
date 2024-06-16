package com.ageurdo.demo_user_auth_api;

import com.ageurdo.demo_user_auth_api.jwt.JwtToken;
import com.ageurdo.demo_user_auth_api.web.dto.UserLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String cpf, String password) {
        String token = client
                .post()
                .uri("/api/auth")
                .bodyValue(new UserLoginDto(cpf, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();
//        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, token);

    }
}
