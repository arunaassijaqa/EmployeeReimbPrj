package com.Revature.Models.DTOs;

//This DTO will only send user id and username to the front end,
//so we don't have to risk sending the user's password over HTTP
public class OutgoingUserDTO
{
    private int userId;

    private String username;

    private String firstname;

    private String lastname;

    private String role;

    private String JWT;

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }



    public OutgoingUserDTO(int userId,  String username , String firstname, String lastname, String role,String JWT) {
        this.userId = userId;
        this.JWT = JWT;
        this.role = role;
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
    }

    public OutgoingUserDTO(int userId, String username, String firstname, String lastname, String role) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public OutgoingUserDTO(int userId, String username, String JWT) {
        this.userId = userId;
        this.username = username;
        this.JWT = JWT;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role='" + role + '\'' +
                ", JWT='" + JWT + '\'' +
                '}';
    }
}
