package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ContextConfiguration(initializers = {TestContainerInitializer.class})
@ActiveProfiles("integrationTest")
public class IntegrationTestSetting {
    @Autowired
    protected MockMvc mvc;
    protected static ObjectMapper objectMapper = new ObjectMapper();
}
