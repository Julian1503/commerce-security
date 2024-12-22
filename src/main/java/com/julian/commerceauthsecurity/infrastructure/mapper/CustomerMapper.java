package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Customer;
import com.julian.commerceauthsecurity.domain.valueobject.Address;
import com.julian.commerceauthsecurity.infrastructure.entity.CustomerEntity;
import com.julian.commerceshared.repository.Mapper;

public class CustomerMapper implements Mapper<Customer, CustomerEntity> {

    public CustomerEntity toSource(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("CustomerMapper.toSource: Customer Model cannot be null");
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName().getValue());
        entity.setLastName(customer.getLastName().getValue());
        entity.setPhoneNumber(customer.getPhoneNumber().getValue());
        entity.setFingerPrintData(customer.getFingerPrintData());
        entity.setPhoto(customer.getPhoto());
        entity.setStreet(customer.getAddress().getStreet());
        entity.setHouseNumber(customer.getAddress().getHouseNumber());
        entity.setFloor(customer.getAddress().getFloor());
        entity.setDoor(customer.getAddress().getDoor());
        entity.setGender(customer.getGender());
        entity.setBirthDate(customer.getBirthDate());
        return entity;
    }

    public Customer toTarget(CustomerEntity entity) {
        if (entity == null) throw new IllegalArgumentException("CustomerMapper.toTarget: Customer Entity cannot be null");
        return Customer.create(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getFingerPrintData(),
                entity.getPhoto(),
                Address.create(entity.getStreet(), entity.getHouseNumber(), entity.getFloor(), entity.getDoor()),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }
}
