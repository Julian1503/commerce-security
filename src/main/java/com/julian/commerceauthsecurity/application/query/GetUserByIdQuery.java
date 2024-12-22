package com.julian.commerceauthsecurity.application.query;

import java.util.UUID;

public class GetUserByIdQuery {
    private UUID userId;
    public GetUserByIdQuery(UUID userId) {
        this.userId = userId;
    }
    public UUID getUserId() {
        return this.userId;
    }
}
