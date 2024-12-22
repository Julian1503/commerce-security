package com.julian.commerceauthsecurity.domain.valueobject;

import com.julian.commerceshared.valueobject.AbstractValueObject;

public class Name extends AbstractValueObject<String> {
    private Name(String value) {
    super(value);
    }

    public static Name create(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (!value.matches("^[a-zA-Z0-9._-]{3,}$")) {
            throw new IllegalArgumentException("Name must contain only letters, numbers, dots, underscores, and hyphens");
        }

        return new Name(value);
    }
}
