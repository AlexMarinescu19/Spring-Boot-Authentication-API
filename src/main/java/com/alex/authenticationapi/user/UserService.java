package com.alex.authenticationapi.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    // C
    public UserResponse register(UserRegister userRegister)
    {
        AppUser newUser = new AppUser();
        newUser.setName(userRegister.getName());
        newUser.setUsername(userRegister.getUsername());
        newUser.setEmail(userRegister.getEmail());
        newUser.setPassword(userRegister.getPassword());

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

    // U

    // D

}
