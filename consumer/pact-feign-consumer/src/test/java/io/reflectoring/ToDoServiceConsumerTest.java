package io.reflectoring;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
@PactTestFor(providerName = "todoservice", port = "8888")
@SpringBootTest({
        // overriding provider address
        "todoservice.ribbon.listOfServers: localhost:8888"
})
public class ToDoServiceConsumerTest {

    @Autowired
    private TodoClient todoClient;

    @Pact(provider = "todoservice", consumer = "todoclient")
    public RequestResponsePact createTodoPact(PactDslWithProvider builder) {
        // @formatter:off
        return builder
                .given("provider accepts a new todo")
                .uponReceiving("a request to POST a todo")
                .path("/todos")
                .method("POST")
                .willRespondWith()
                .status(201)
                .matchHeader("Content-Type", "application/json")
                .body(new PactDslJsonBody()
                        .integerType("id", 42))
                .toPact();
        // @formatter:on
    }

    @Test
    @PactTestFor(pactMethod = "createTodoPact")
    public void verifyCreateTodoPact() {
        Todo todo = new Todo();
        todo.setTitle("Dig large hole");
        todo.setCompleted(false);
        IdObject id = todoClient.createTodo(todo);
        Assertions.assertEquals(id.getId(), 42);
    }

}