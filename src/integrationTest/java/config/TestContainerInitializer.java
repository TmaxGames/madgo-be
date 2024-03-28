package config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

public class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public static final int TIBERO_PORT = 8629;
    public static final int REDIS_PORT = 6379;;
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        DockerComposeContainer container = new DockerComposeContainer(new File("src/integrationTest/resources/test-database-compose.yml"));

        container.start();
    }
}

