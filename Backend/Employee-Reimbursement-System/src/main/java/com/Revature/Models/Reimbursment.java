package com.Revature.Models;

import jakarta.persistence.*;
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
    //@OnDelete(action = OnDeleteAction.CASCADE)
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

    public Reimbursment(int amount, String description, String status, User user) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.user = user;



    }
}
