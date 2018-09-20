package io.reflectoring;

import au.com.dius.pact.provider.junit.State;

import java.util.Arrays;

import static org.mockito.Mockito.when;

public interface PersonAlreadyExistsState {

    @State({"person Hans Oberlander exists"})
    default void personAlreadyExistsState() {
        User hans[] = { new User("Hans", "Oberlander") };
        when(getUserRepository().findAll()).thenReturn(Arrays.asList(hans));
    }

    UserRepository getUserRepository();
}
