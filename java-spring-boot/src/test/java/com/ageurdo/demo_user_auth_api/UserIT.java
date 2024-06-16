package com.ageurdo.demo_user_auth_api;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.web.dto.UserCreateDto;
import com.ageurdo.demo_user_auth_api.web.dto.UserPasswordDto;
import com.ageurdo.demo_user_auth_api.web.dto.UserResponseDto;
import com.ageurdo.demo_user_auth_api.web.dto.UserUpdateDto;
import com.ageurdo.demo_user_auth_api.web.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)

public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_WithCpfAndPassword_ReturnCreatedUserWithStatusCode201(){
        UserResponseDto responseBody = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "56418817087",
                        "12345678",
                        "Fernanda Rosa Eduarda Ramos",
                        LocalDateTime.parse("1988-10-10T10:00:00"),
                        null,
                        "Rua Corações",
                        "779",
                        "Casa final da rua",
                        "Jardim Lancaster",
                        "Foz do Iguaçu",
                        "PR",
                        "85851-450"
                ))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("56418817087");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDateOfBirth()).isEqualTo("1988-10-10T10:00:00");
    }

    @Test
    public void createUser_WithInvalidCpf_ReturnErrorMessageWithStatusCode422(){
        //  Empty CPF
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "",
                        "87654321",
                        "João Silva",
                        LocalDateTime.parse("1990-02-20T10:00:00"),
                        null,
                        "Rua da Praia",
                        "456",
                        "Casa 2",
                        "Praia Grande",
                        "Santos",
                        "SP",
                        "11015-000"
                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        //  Invalid CPF
        responseBody = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "123.456.789.00",
                        "87654321",
                        "João Silva",
                        LocalDateTime.parse("1990-02-20T10:00:00"),
                        null,
                        "Rua da Praia",
                        "456",
                        "Casa 2",
                        "Praia Grande",
                        "Santos",
                        "SP",
                        "11015-000"
                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUser_WithInvalidPassword_ReturnErrorMessageWithStatusCode422(){
        //  Empty password
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "08163899930",
                        "",
                        "João Silva",
                        LocalDateTime.parse("1990-02-20T10:00:00"),
                        null,
                        "Rua da Praia",
                        "456",
                        "Casa 2",
                        "Praia Grande",
                        "Santos",
                        "SP",
                        "11015-000"
                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        //  Password smaller than 8 digits
        responseBody = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "07450156970",
                        "123",
                        "João Silva",
                        LocalDateTime.parse("1990-02-20T10:00:00"),
                        null,
                        "Rua da Praia",
                        "456",
                        "Casa 2",
                        "Praia Grande",
                        "Santos",
                        "SP",
                        "11015-000"
                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        //  Password bigger than 8 digits
        responseBody = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "07450156970",
                        "87654321000000",
                        "João Silva",
                        LocalDateTime.parse("1990-02-20T10:00:00"),
                        null,
                        "Rua da Praia",
                        "456",
                        "Casa 2",
                        "Praia Grande",
                        "Santos",
                        "SP",
                        "11015-000"
                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUser_WithDuplicatedCpf_ReturnErrorMessageWithStatusCode409(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "73553655500",
                        "12345678",
                        "João Silva",
                        LocalDateTime.parse("1990-02-20T10:00:00"),
                        null,
                        "Rua da Praia",
                        "456",
                        "Casa 2",
                        "Praia Grande",
                        "Santos",
                        "SP",
                        "11015-000"
                ))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void getUser_WithExistingId_ReturnUserWithStatusCode200(){
        UserResponseDto responseBody = testClient
                .get()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1002);
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("35774538699");
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Kaique Benício Emanuel Fernandes");
    }

    @Test
    public void getUser_WithNonExistentId_ReturnErrorMessageWithStatusCode404(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "73553655500", "12345678" ))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void updatePassword_WithValidData_ReturnStatusCode204(){
        testClient
                .patch()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "12345678",
                        "10101010",
                        "10101010"

                ))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void updatePassword_WithAnotherUsersId_ReturnErrorMessageWithStatusCode403(){

        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "12345678",
                        "20202020",
                        "20202020"

                ))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

    @Test
    public void updatePassword_WithInvalidPasswords_ReturnErrorMessageWithStatusCode400(){
        // Empty password
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "",
                        "",
                        ""

                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        // Password less than 8 digits
        responseBody = testClient
                .patch()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "1234567",
                        "1234567",
                        "1234567"

                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        // Password bigger than 8 digits
        responseBody = testClient
                .patch()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "123465789",
                        "123456789",
                        "123456789"

                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        // New password no matach with confirm password
        responseBody = testClient
                .patch()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "12345678",
                        "11111111",
                        "00000000"

                ))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        // Current password wrong
        responseBody = testClient
                .patch()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "00000000",
                        "12345678",
                        "12345678"

                ))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

    }

    @Test
    public void updateUser_WithValidData_ReturnUserWithStatusCode200(){

        UserUpdateDto userToUpdate = new UserUpdateDto(
                "João Silva Updated",
                LocalDateTime.parse("1990-02-20T10:00:00"),
                "Rua da Praia Updated",
                "456",
                "Casa 2 Updated",
                "Praia Grande Updated",
                "Santos Updated",
                "SP Updated",
                "11015-000 Updated"
        );

        UserResponseDto responseBody = testClient
                .put()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1002);
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("João Silva Updated");
    }

    @Test
    public void updateUser_WithAnotherUsersId_ReturnErrorMessageWithStatusCode403(){
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdateDto(
                        "João Silva Updated",
                        LocalDateTime.parse("1990-02-20T10:00:00"),
                        "Rua da Praia Updated",
                        "456",
                        "Casa 2 Updated",
                        "Praia Grande Updated",
                        "Santos Updated",
                        "SP Updated",
                        "11015-000 Updated"
                ))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void deleteUser_WithValidData_ReturnUserWithStatusCode200(){
        Void responseBody = testClient
                .delete()
                .uri("/api/users/1002")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "73553655500", "12345678" ))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(void.class)
                .returnResult().getResponseBody();
    }

    @Test
    public void deleteUser_WithAnotherUsersId_ReturnErrorMessageWithStatusCode403(){
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void getAllUsers_WithAdminUser_ReturnUserWithStatusCode200(){
        List<UserResponseDto> responseBody = testClient
                .get()
                .uri("/api/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "73553655500", "12345678" ))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
//        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
    }

    @Test
    public void getAllUsers_WithBasicUser_ReturnUserWithStatusCode403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "35774538699", "12345678" ))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

}
