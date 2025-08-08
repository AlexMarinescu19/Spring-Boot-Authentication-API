package com.alex.authenticationapi.user;

public class UserResponse
{
    private String name;
    private String username;
    private String email;
    private Role role;

    public UserResponse() {}

    public UserResponse(String name, String username, String email, Role role)
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getName()
    {
        return name;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public Role getRole()
    {
        return role;
    }
}
