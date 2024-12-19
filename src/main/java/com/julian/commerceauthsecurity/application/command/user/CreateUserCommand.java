package com.julian.commerceauthsecurity.application.command.user;

public class CreateUserCommand {
    private String username;
    private String email;
    private String password;

    public CreateUserCommand() {
    }

    public CreateUserCommand(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
