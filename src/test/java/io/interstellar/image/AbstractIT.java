package io.interstellar.image;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Common parent for all integration tests, that starts the spring context.
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractIT {
}
