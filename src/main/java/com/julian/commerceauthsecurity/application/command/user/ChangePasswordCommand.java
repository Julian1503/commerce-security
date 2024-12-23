package com.julian.commerceauthsecurity.application.command.user;

public record ChangePasswordCommand(String username, String oldPassword, String newPassword) {

}
