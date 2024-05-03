package com.Revature.Models.DTOs;

public class IncomingReimbDTO {

    private String description;

    private int amount;

    private String status;

    private int userId;


    public IncomingReimbDTO() {
    }

    public IncomingReimbDTO(int amount, String status, int userId, String description) {
        this.amount = amount;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }

    public IncomingReimbDTO(String description, int amount, int userId) {
        this.description = description;
        this.amount = amount;
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "IncomingReimbDTO{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}
