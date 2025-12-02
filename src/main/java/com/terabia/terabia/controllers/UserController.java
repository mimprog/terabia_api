package com.terabia.terabia.controllers;

import com.terabia.terabia.chat.ChatService;
import com.terabia.terabia.dto.UpdateUserDto;
import com.terabia.terabia.models.User;
import com.terabia.terabia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List <User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam("q") String query,
            @RequestHeader("Authorization") String token) {

        List<User> users = chatService.searchUsers(query);

        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity < ? > updateUser(@PathVariable Integer id, @RequestBody UpdateUserDto updateUserDto) {
        UpdateUserDto updateUserDto1 =  userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updateUserDto1);

    }

}
