package com.bank.service;

import com.bank.dto.AddressInfo;
import com.bank.entity.Address;
import org.springframework.stereotype.Service;

/**
 * Created by eniakel on 01/09/2015.
 */
@Service
public class AddressService {
    public AddressInfo mapToDto(Address address) {
        AddressInfo dto = new AddressInfo();
        dto.setHouseNumber(address.getHouseNumber());
        dto.setStreetName(address.getStreetName());
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setEirCode(address.getEirCode());
        return dto;
    }

    public Address mapToEntity(AddressInfo addressInfo) {
        Address entity = new Address();
        entity.setHouseNumber(addressInfo.getHouseNumber());
        entity.setStreetName(addressInfo.getStreetName());
        entity.setCity(addressInfo.getCity());
        entity.setCountry(addressInfo.getCountry());
        entity.setEirCode(addressInfo.getEirCode());
        return entity;
    }
}
