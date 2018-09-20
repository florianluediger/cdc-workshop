package io.reflectoring;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "userservice")
public interface UserClient {

    @RequestMapping(method = RequestMethod.POST, path = "/user-service/users")
    IdObject createUser(@RequestBody User user);
}
