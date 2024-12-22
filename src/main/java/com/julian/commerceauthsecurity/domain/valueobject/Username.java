package com.julian.commerceauthsecurity.domain.valueobject;

import com.julian.commerceshared.valueobject.AbstractValueObject;

public class Username extends AbstractValueObject<String> {

    private Username(String value) {
        super(value);
    }

    public static Username create(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (!value.matches("^[a-zA-Z0-9._-]{3,}$")) {
            throw new IllegalArgumentException("Username must contain only letters, numbers, dots, underscores, and hyphens");
        }
        return new Username(value);
    }
}
