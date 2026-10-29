package com.aloha.shop.repository.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.shop.domain.users.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * 회원의 주소 목록을 기본 주소 우선으로 정렬하여 조회합니다.
     * SELECT *
     * FROM address
     * WHERE user_no = :userNo
     * ORDER BY default_address DESC, no DESC
     * @param userNo
     * @return
     */
    List<Address> findByUserNoOrderByDefaultAddressDescNoDesc(Long userNo);
}
