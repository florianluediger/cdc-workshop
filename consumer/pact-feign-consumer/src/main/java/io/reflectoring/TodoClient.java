package io.reflectoring;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "todoservice")
public interface TodoClient {

    @RequestMapping(method = RequestMethod.POST, path = "/todos")
    IdObject createTodo(@RequestBody Todo todo);
}
