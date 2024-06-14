package com.ageurdo.demo_user_auth_api;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.web.dto.UserCreateDto;
import com.ageurdo.demo_user_auth_api.web.dto.UserPasswordDto;
import com.ageurdo.demo_user_auth_api.web.dto.UserResponseDto;
import com.ageurdo.demo_user_auth_api.web.dto.UserUpdateDto;
import com.ageurdo.demo_user_auth_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
                        "542.099.440-25",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("542.099.440-25");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDateOfBirth()).isEqualTo("1990-02-20T10:00:00");
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
                        "07450156970",
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
    public void getUser_WithExistingtId_ReturnUserWithStatusCode200(){
        // First insert one user
        UserResponseDto UserCreatedForTest = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "18954246079",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        // Save Id to Compare in validations below
        Long userId = UserCreatedForTest.getId();

        UserResponseDto responseBody = testClient
                .get()
                .uri("/api/users/" + userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(userId);
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("18954246079");
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("João Silva");
    }

    @Test
    public void getUser_WithNonExistentId_ReturnErrorMessageWithStatusCode404(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/users/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void updatePassword_WithValidData_ReturnStatusCode204(){

        // First insert one user
        UserResponseDto UserCreatedForTest = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "18954246079",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        // Save Id to Compare in validations below
        Long userId = UserCreatedForTest.getId();

        testClient
                .patch()
                .uri("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "87654321",
                        "88884444",
                        "88884444"

                ))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void updatePassword_WithNonExistentId_ReturnErrorMessageWithStatusCode404(){

        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/users/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "87654321",
                        "88884444",
                        "88884444"

                ))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void updatePassword_WithNonExistentId_ReturnErrorMessageWithStatusCode422(){

        // First insert one user
        UserResponseDto UserCreatedForTest = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "18954246079",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        // Save Id to Compare in validations below
        Long userId = UserCreatedForTest.getId();

        // Empty password
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/users/" + userId)
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

        // Password less than 6 digits
        responseBody = testClient
                .patch()
                .uri("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto(
                        "12345",
                        "12345",
                        "12345"

                ))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        // Password bigger than 6 digits
        responseBody = testClient
                .patch()
                .uri("/api/users/" + userId)
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

    }

    @Test
    public void updatePassword_WithInvalidPasswords_ReturnErrorMessageWithStatusCode400(){

        // First insert one user
        UserResponseDto UserCreatedForTest = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "18954246079",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        // Save Id to Compare in validations below
        Long userId = UserCreatedForTest.getId();

        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/users/" + userId)
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

        responseBody = testClient
                .patch()
                .uri("/api/users/" + userId)
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
        // First insert one user
        UserResponseDto userCreatedForTest = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "18954246079",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        // Save Id to Compare in validations below
        Long userId = userCreatedForTest.getId();

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
                .uri("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(userId);
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("João Silva Updated");
    }

    @Test
    public void updateUser_WithNonExistentId_ReturnErrorMessageWithStatusCode404(){
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/users/0")
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
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deleteUser_WithValidData_ReturnUserWithStatusCode200(){
        // First insert one user
        UserResponseDto userCreatedForTest = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "18954246079",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        // Save Id to Compare in validations below
        Long userId = userCreatedForTest.getId();

        Void responseBody = testClient
                .delete()
                .uri("/api/users/" + userId)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class)
                .returnResult().getResponseBody();
    }

    @Test
    public void deleteUser_WithNonExistentId_ReturnErrorMessageWithStatusCode404(){
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/users/0")
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
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void getAllUsers_WithValidData_ReturnUserWithStatusCode200(){

        // First insert one user
        UserResponseDto UserCreatedForTest = testClient
                .post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto(
                        "18954246079",
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
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        List<UserResponseDto> responseBody = testClient
                .get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

}
