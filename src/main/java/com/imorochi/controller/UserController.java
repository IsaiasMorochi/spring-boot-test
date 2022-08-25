package com.imorochi.controller;

import com.imorochi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @GetMapping(value = "/{name}")
    public ResponseEntity<?> findUserById(@PathVariable(name = "name") String name) {
        Map<String, User> users = new HashMap<>();
        users.put("PivotalSoftware", new User("PivotalSoftware","PivotalSoftware"));
        users.put("CloudFoundry", new User("CloudFoundry","CloudFoundry"));
        users.put("Spring-Projects", new User("Spring-Projects","Spring-Projects"));
        users.put("RameshMF", new User("RameshMF","RameshMF"));
        return new ResponseEntity<>(users.get(name), HttpStatus.OK);
    }

}
