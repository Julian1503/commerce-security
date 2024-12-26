package com.julian.commerceauthsecurity.domain.valueobject;

import com.julian.commerceshared.valueobject.AbstractValueObject;

public class SecurityName extends AbstractValueObject<String> {
    private SecurityName(String value) {
        super(value);
    }

    public static SecurityName create(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (!value.matches("^[a-zA-Z._-]{3,}$")) {
            throw new IllegalArgumentException("Name must contain only letters, numbers, dots, underscores, and hyphens");
        }

        return new SecurityName(value.toUpperCase());
    }
}
