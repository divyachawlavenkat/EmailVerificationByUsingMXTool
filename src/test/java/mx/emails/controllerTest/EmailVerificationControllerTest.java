package mx.emails.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailVerificationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testValidEmail() {
        String email = "example@example.com";
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/verify-email?email=" + email, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email is valid.", response.getBody());
    }

    @Test
    public void testInvalidEmail() {
        String email = "invalid_email";
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/verify-email?email=" + email, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email is invalid.", response.getBody());
    }
}
