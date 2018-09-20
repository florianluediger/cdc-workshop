package io.reflectoring;

import au.com.dius.pact.provider.junit.State;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public interface ToCreatePersonState {

    @State({"provider accepts a new person"})
    default void toCreatePersonState() {
        User user = new User("Arthur", "Dent");
        user.setId(42L);
        UserRepository ur = getUserRepository();
        when(ur.findById(42L)).thenReturn(Optional.of(user));
        when(ur.save(any(User.class))).thenReturn(user);
    }

    UserRepository getUserRepository();
}
