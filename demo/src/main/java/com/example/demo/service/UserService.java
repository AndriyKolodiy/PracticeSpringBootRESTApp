package com.example.demo.service;

import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService<user> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }


    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new IllegalStateException("Email taken!))");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long user_Id) {
        boolean exists = userRepository.existsById(user_Id);
        if(!exists){
            throw new IllegalStateException("User with id = "+user_Id+" doesn't exists!");
        }
        userRepository.deleteById(user_Id);
    }

    @Transactional
    public void updateUser(Long userId, String username, String email, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalStateException("User with id = "+userId+" doesn't exists"));

        if(username!=null&&
                username.length()>0&&
        !Objects.equals(user.getUsername(), username)){
            user.setUsername(username);
        }

        if(password!=null&&
                password.length()>0&&
                !Objects.equals(user.getPassword(), password)){
            user.setUsername(password);
        }

        if(email!=null&&
                email.length()>0&&
        !Objects.equals(user.getEmail(), email)){
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if (userOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            user.setEmail(email);
        }

    }

    public User getUserId(String email, String password) {
        User user = userRepository.findUserByAuthData(email, password);

        if(password!=null&&password.length()>0){
            if(email!=null&&email.length()>0){
                Optional<User> userOptional = userRepository.findUserByEmail(email);
            }
        }

        return user;
    }
}
