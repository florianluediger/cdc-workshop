package io.reflectoring;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8080")
@Provider("userservice")
@PactFolder("../../consumer/pact-feign-consumer/target/pacts")
//@PactBroker(host = "adesso.pact.dius.com.au", port = "443", protocol = "https",
//        authentication = @PactBrokerAuth(username = "Vm6YWrQURJ1T7mDIRiKwfexCAc4HbU", password = "aLerJwBhpEcN0Wm88Wgvs45AR9dXpc"))
//@PactFilter("provider accepts a new person")
public class UserControllerProviderTest implements ToCreatePersonState, PersonAlreadyExistsState {

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8080, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }
}
