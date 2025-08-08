package com.alex.authenticationapi.user;

import com.alex.authenticationapi.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // C
    public UserResponse register(UserRegister userRegister)
    {
        AppUser newUser = new AppUser();
        newUser.setName(userRegister.getName());
        newUser.setUsername(userRegister.getUsername());
        newUser.setEmail(userRegister.getEmail());

        String hashedPassword = passwordEncoder.encode(userRegister.getPassword());
        newUser.setPassword(hashedPassword);

        AppUser savedUser = userRepository.save(newUser);

        return new UserResponse(
                savedUser.getName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    // R
    public List<UserResponse> getAllUsers()
    {
        return userRepository.findAll().stream()
                .map(appUser -> new UserResponse(
                        appUser.getName(),
                        appUser.getUsername(),
                        appUser.getEmail(),
                        appUser.getRole()
                ))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Integer id)
    {
        return userRepository.findById(id)
                .map(appUser -> new UserResponse(
                        appUser.getName(),
                        appUser.getUsername(),
                        appUser.getEmail(),
                        appUser.getRole()))
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    // U

    // D

}
