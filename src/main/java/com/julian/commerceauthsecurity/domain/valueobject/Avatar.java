package com.julian.commerceauthsecurity.domain.valueobject;

import com.julian.commerceshared.valueobject.AbstractValueObject;

public class Avatar extends AbstractValueObject<String> {

    private Avatar(String value) {
        super(value);
    }

    public static Avatar create(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Avatar cannot be null or empty");
        }
        return new Avatar(value);
    }

    public static String getDefaultAvatar() {
        return "https://www.gravatar.com/avatar/";
    }
}
