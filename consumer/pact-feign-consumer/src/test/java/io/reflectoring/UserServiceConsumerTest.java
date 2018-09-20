package io.reflectoring;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
@PactTestFor(providerName = "userservice", port = "8888")
@SpringBootTest({
        // overriding provider address
        "userservice.ribbon.listOfServers: localhost:8888"
})
public class UserServiceConsumerTest {

    @Autowired
    private UserClient userClient;

    @Pact(provider = "userservice", consumer = "myuserclient")
    public RequestResponsePact createPersonPact(PactDslWithProvider builder) {
        return builder
                .given("provider accepts a new person")
                .uponReceiving("a request to POST a person")
                .path("/user-service/users")
                .method("POST")
                .body(new PactDslJsonBody()
                .stringType("firstName", "Arthur")
                .stringType("lastName", "Dent")
                .integerType("id", 42))
                .willRespondWith()
                .status(201)
                .matchHeader("Content-Type", "application/json;charset=UTF-8")
                .body(new PactDslJsonBody()
                        .integerType("id", 42))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPersonPact")
    public void verifyCreatePersonPact() {
        User user = new User(42L, "Zahphod", "Beeblebrox");
        IdObject id = userClient.createUser(user);
        Assertions.assertEquals(id.getId(), 42);
    }

    @Pact(provider = "userservice", consumer = "myuserclient")
    public RequestResponsePact verifyCreateAlreadyExistingUserPact(PactDslWithProvider builder) {
        return builder
                .given("person Hans Oberlander exists")
                .uponReceiving("a request to create Hans Oberlander")
                .path("/user-service/users")
                .method("POST")
                .body(new PactDslJsonBody()
                        .stringType("firstName", "Hans")
                        .stringType("lastName", "Oberlander")
                        .integerType("id", 4711))
                .willRespondWith()
                .status(400)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "verifyCreateAlreadyExistingUserPact")
    public void verifyCreateAlreadyExistingUserPact() {
        User hans = new User(4711L, "Hans", "Oberlander");
        Assertions.assertThrows(FeignException.class, () -> {
            userClient.createUser(hans);
        });
    }
}
