package learn.solarfarm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("jdbc")
class AppTestJdbc {

    @Test
    void applicationShouldStart() {

    }
}