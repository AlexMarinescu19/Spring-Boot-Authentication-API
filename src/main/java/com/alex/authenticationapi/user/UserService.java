package com.alex.authenticationapi.user;

import com.alex.authenticationapi.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public UserResponse registerUser(UserRegister userRegister)
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
    public UserResponse updateUser(Integer id, UserPartialUpdate userPartialUpdate)
    {
        return userRepository.findById(id)
                .map(appUser -> {
                    if (userPartialUpdate.getName() != null)
                        appUser.setName(userPartialUpdate.getName());

                    if (userPartialUpdate.getUsername() != null)
                        appUser.setUsername(userPartialUpdate.getUsername());

                    if (userPartialUpdate.getEmail() != null)
                        appUser.setEmail(userPartialUpdate.getEmail());

                    if (userPartialUpdate.getPassword() != null)
                        appUser.setPassword(passwordEncoder.encode(userPartialUpdate.getPassword()));

                    AppUser updatedUser = userRepository.save(appUser);

                    return new UserResponse(
                            updatedUser.getName(),
                            updatedUser.getUsername(),
                            updatedUser.getEmail(),
                            updatedUser.getRole()
                    );
                })
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    // D
    public void deleteUserById(Integer id)
    {
        if (!userRepository.existsById(id))
        {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }

        userRepository.deleteById(id);
    }

}
