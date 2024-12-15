package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Customer;
import com.julian.commerceauthsecurity.infrastructure.entity.CustomerEntity;
import com.julian.commerceshared.repository.Mapper;

public class CustomerMapper implements Mapper<Customer, CustomerEntity> {

    public CustomerEntity toEntity(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("CustomerMapper.toEntity: Customer Model cannot be null");
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setLastName(customer.getLastName());
        entity.setPhoneNumber(customer.getPhoneNumber());
        entity.setFingerPrintData(customer.getFingerPrintData());
        entity.setPhoto(customer.getPhoto());
        entity.setStreet(customer.getStreet());
        entity.setHouseNumber(customer.getHouseNumber());
        entity.setFloor(customer.getFloor());
        entity.setDoor(customer.getDoor());
        entity.setGender(customer.getGender());
        entity.setBirthDate(customer.getBirthDate());
        return entity;
    }

    public Customer toDomainModel(CustomerEntity entity) {
        if (entity == null) throw new IllegalArgumentException("CustomerMapper.toDomainModel: Customer Entity cannot be null");
        return Customer.create(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getFingerPrintData(),
                entity.getPhoto(),
                entity.getStreet(),
                entity.getHouseNumber(),
                entity.getFloor(),
                entity.getDoor(),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }
}
