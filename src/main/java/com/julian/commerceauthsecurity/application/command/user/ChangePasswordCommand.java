package com.julian.commerceauthsecurity.application.command.user;

public class ChangePasswordCommand {
    private String username;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordCommand() {
    }

    public ChangePasswordCommand(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return this.username;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }
}
