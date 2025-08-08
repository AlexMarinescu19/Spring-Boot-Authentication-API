package com.alex.authenticationapi.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegister userRegister)
    {
        UserResponse response = userService.register(userRegister);
        URI location = URI.create("/api/user/" + response.getUsername());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        List<UserResponse> users = userService.getAllUsers();

        return users.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id)
    {
        UserResponse foundUser = userService.getUserById(id);

        return ResponseEntity.ok(foundUser);
    }
}
