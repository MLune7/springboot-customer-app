package com.example.luna_323971416.service;

import com.example.luna_323971416.model.Customer;
import com.example.luna_323971416.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer addCustomer(Customer c) {
        assignCategoryAndPoints(c);
        return repo.save(c);
    }

    public Customer getCustomertById(Long id){
        return repo.findById(id).orElse(null);
    }

    public Customer updateBalance(Customer c) {
        Customer cx = getCustomertById(c.getId());
        if (cx != null) {
            cx.setBalance(c.getBalance());
            assignCategoryAndPoints(cx);
            return repo.save(cx);
        }
        return null;
    }

    public Customer updateCustomer(Customer c) {
        Customer existing = getCustomertById(c.getId());
        if (existing != null) {
            if (c.getFirstName() != null) existing.setFirstName(c.getFirstName());
            if (c.getLastName() != null) existing.setLastName(c.getLastName());
            if (c.getBalance() >= 0) existing.setBalance(c.getBalance());
            assignCategoryAndPoints(existing);
            return repo.save(existing);
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        repo.deleteById(id);
    }

    public List<Customer> getByCategory(String category) {
        return repo.findByCategory(category);
    }

    public List<Customer> getByBalanceGreaterThan(int amount) {
        return repo.findByBalanceGreaterThan(amount);
    }

    public Page<Customer> getPagedCustomers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return repo.findAll(pageable);
    }

    private void assignCategoryAndPoints(Customer c) {
        if (c.getBalance() > 5000) {
            c.setCategory("VIP");
        } else if (c.getBalance() > 1000) {
            c.setCategory("Premium");
        } else {
            c.setCategory("Regular");
        }
        c.setPoints(c.getBalance() / 100);
    }
}
