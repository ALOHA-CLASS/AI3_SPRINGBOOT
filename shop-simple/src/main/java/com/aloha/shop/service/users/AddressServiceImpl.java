package com.aloha.shop.service.users;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.shop.domain.users.Address;
import com.aloha.shop.domain.users.User;
import com.aloha.shop.dto.users.AddressDto;
import com.aloha.shop.repository.users.AddressRepository;
import com.aloha.shop.repository.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public List<Address> list(Long userNo) {
        return addressRepository.findByUserNoOrderByDefaultAddressDescNoDesc(userNo);
    }

    @Override
    @Transactional
    public Address add(Long userNo, AddressDto dto) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (dto.isDefaultAddress()) {
            clearDefault(userNo);
        }

        Address address = Address.builder()
                .user(user)
                .receiver(dto.getReceiver())
                .phone(dto.getPhone())
                .zipcode(dto.getZipcode())
                .address1(dto.getAddress1())
                .address2(dto.getAddress2())
                .defaultAddress(dto.isDefaultAddress())
                .build();
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address update(Long userNo, AddressDto dto) {
        Address address = getOwned(userNo, dto.getNo());
        if (dto.isDefaultAddress()) {
            clearDefault(userNo);
        }
        address.setReceiver(dto.getReceiver());
        address.setPhone(dto.getPhone());
        address.setZipcode(dto.getZipcode());
        address.setAddress1(dto.getAddress1());
        address.setAddress2(dto.getAddress2());
        address.setDefaultAddress(dto.isDefaultAddress());
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(Long userNo, Long addressNo) {
        Address address = getOwned(userNo, addressNo);
        addressRepository.delete(address);
    }

    private Address getOwned(Long userNo, Long addressNo) {
        Address address = addressRepository.findById(addressNo)
                .orElseThrow(() -> new IllegalArgumentException("배송지를 찾을 수 없습니다."));
        if (!address.getUser().getNo().equals(userNo)) {
            throw new IllegalArgumentException("본인의 배송지가 아닙니다.");
        }
        return address;
    }

    private void clearDefault(Long userNo) {
        for (Address a : addressRepository.findByUserNoOrderByDefaultAddressDescNoDesc(userNo)) {
            if (a.isDefaultAddress()) {
                a.setDefaultAddress(false);
                addressRepository.save(a);
            }
        }
    }
}
