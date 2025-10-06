package com.example.luna_323971416.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("balance")
    private int balance;

    @JsonProperty("category")
    private String category;

    @JsonProperty("points")
    private int points;

    public Customer() {}

    public Customer(String category, int balance, String lastName, String firstName) {
        this.category = category;
        this.balance = balance;
        this.lastName = lastName;
        this.firstName = firstName;
        this.points = balance / 100;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
