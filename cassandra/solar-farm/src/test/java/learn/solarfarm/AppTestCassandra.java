package learn.solarfarm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("cassandra")
class AppTestCassandra {

    @Test
    void applicationShouldStart() {

    }
}