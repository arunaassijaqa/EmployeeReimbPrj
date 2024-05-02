package com.Revature.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="reimbursment")
public class Reimbursment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbId;

    private String description;

    @Column(nullable = false)
    private int amount;


    private String status = "pending";


    @ManyToOne(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Reimbursment() {
    }

    public Reimbursment(int reimbId, String description, int amount, String status, User user) {
        this.reimbId = reimbId;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }

    public Reimbursment(int reimbId, String description, int amount, User user) {
        this.reimbId = reimbId;
        this.description = description;
        this.amount = amount;
        this.user = user;
    }

    public Reimbursment(int amount, String description, String status, User user) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public Reimbursment(int amount, String description, User user) {
        this.amount = amount;
        this.description = description;
        this.user = user;
    }

    public Reimbursment(User user) {
        this.user = user;
    }

    public Reimbursment(String description, int amount, User user) {
        this.description = description;
        this.amount = amount;
        this.user = user;
    }

    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reimbursment{" +
                "reimbId=" + reimbId +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
