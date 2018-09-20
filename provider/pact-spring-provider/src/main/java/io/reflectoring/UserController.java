package io.reflectoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.StreamSupport;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/user-service/users")
    public ResponseEntity<IdObject> createUser(@RequestBody @Valid User user) {
        if (StreamSupport.stream(userRepository.findAll().spliterator(), false)
        .anyMatch(element -> element.getLastName().equals(user.getLastName()) && element.getFirstName().equals(user.getFirstName())))
            return ResponseEntity
                    .status(400)
                    .body(new IdObject(0));

        User savedUser = this.userRepository.save(user);
        return ResponseEntity
                .status(201)
                .body(new IdObject(savedUser.getId()));
    }
}
