package com.jpa_Test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa_Test.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
