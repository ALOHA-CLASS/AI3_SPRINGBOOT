package com.aloha.shop.service.users;

import java.util.List;

import com.aloha.shop.domain.users.Address;
import com.aloha.shop.dto.users.AddressDto;

public interface AddressService {

    List<Address> list(Long userNo);

    Address add(Long userNo, AddressDto dto);

    Address update(Long userNo, AddressDto dto);

    void delete(Long userNo, Long addressNo);
}
