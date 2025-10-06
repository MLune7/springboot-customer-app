package com.example.luna_323971416.repository;

import com.example.luna_323971416.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCategory(String category);
    List<Customer> findByBalanceGreaterThan(int balance);
}
