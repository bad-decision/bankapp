package ru.azmeev.bank.exchange.integration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public abstract class IntegrationTestBase {

    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.1");

    static {
        if (System.getenv("CI") != null) {
            System.setProperty("testcontainers.reuse.enable", "true");
            System.setProperty("testcontainers.ryuk.disabled", "true");
            System.setProperty("docker.host", "unix:///var/run/docker.sock");
        }

        postgreSQLContainer.withReuse(true);
        postgreSQLContainer.start();
    }
}