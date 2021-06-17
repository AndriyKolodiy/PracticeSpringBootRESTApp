package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/login")
    public User getId(@RequestParam(required = false) String email,
                        @RequestParam(required = false) String password) throws JSONException {
        return userService.getUserId(email, password);
    }

    @PostMapping("/registration")
    public void registerNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    @DeleteMapping(path = "/user/{userId}")
    public void deleteUser(@PathVariable("userId") Long Id){
        userService.deleteUser(Id);
    }

    @PutMapping(path = "/user/{userId}")
    public void updateUser(@PathVariable("userId") Long userId,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String password){
        userService.updateUser(userId, username, email, password);
    }
}
