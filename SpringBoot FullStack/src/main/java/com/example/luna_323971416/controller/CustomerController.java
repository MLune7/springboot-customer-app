package com.example.luna_323971416.controller;

import com.example.luna_323971416.model.Customer;
import com.example.luna_323971416.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public List<Customer> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer c) {
        return service.addCustomer(c);
    }

    @PostMapping("/edit")
    public Customer updateBalance(@RequestBody Customer c) {
        return service.updateBalance(c);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer c) {
        return service.updateCustomer(c);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
    }

    @GetMapping("/category/{category}")
    public List<Customer> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    @GetMapping("/balance/{amount}")
    public List<Customer> getByBalanceGreaterThan(@PathVariable int amount) {
        return service.getByBalanceGreaterThan(amount);
    }

    @GetMapping("/paged")
    public Page<Customer> getPagedCustomers(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        return service.getPagedCustomers(page, size, sortBy);
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=customers.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,First Name,Last Name,Balance,Category,Points");

        for (Customer c : service.getAll()) {
            writer.println(c.getId() + "," + c.getFirstName() + "," + c.getLastName() + "," +
                    c.getBalance() + "," + c.getCategory() + "," + c.getPoints());
        }

        writer.flush();
        writer.close();
    }
}
