package io.reflectoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TodoController {
    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostMapping(path = "/todos")
    public ResponseEntity<IdObject> createTodo(@RequestBody @Valid Todo todo) {
        Todo savedTodo = this.todoRepository.save(todo);
        return ResponseEntity
                .status(201)
                .body(new IdObject(savedTodo.getId()));
    }
}
