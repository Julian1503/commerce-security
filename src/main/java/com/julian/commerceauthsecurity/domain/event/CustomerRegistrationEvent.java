package com.julian.commerceauthsecurity.domain.event;

import java.util.UUID;

public class CustomerRegistrationEvent {
    private final UUID customerId;

    public CustomerRegistrationEvent(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
