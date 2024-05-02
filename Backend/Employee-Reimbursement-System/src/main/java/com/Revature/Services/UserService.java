package com.Revature.Services;

import com.Revature.DAOs.UserDAO;
import com.Revature.Models.DTOs.IncomingUserDTO;
import com.Revature.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        System.out.println("Inside user service controller");
        this.userDAO = userDAO;
    }

    public User addUser(IncomingUserDTO userDTO){

        System.out.println(" indise the addUser in service layer");

        System.out.println("name   "+userDTO.getUsername());

        System.out.println("pass   "+userDTO.getPassword());
        System.out.println("fname  "+userDTO.getFirstname());
        System.out.println("lname  " +userDTO.getLastname());
        if(userDTO.getUsername().isBlank() || userDTO.getUsername()==null){
            throw  new IllegalArgumentException("Username Cannot be empty");

        }

        if(userDTO.getPassword().isBlank() || userDTO.getPassword()==null){
            throw  new IllegalArgumentException("Password  Cannot be empty");

        }

        if(userDTO.getFirstname().isBlank() || userDTO.getFirstname()==null){
            throw  new IllegalArgumentException("UserFirstname Cannot be empty");

        }

        if(userDTO.getLastname().isBlank() || userDTO.getLastname()==null){
            throw  new IllegalArgumentException("Userlastname Cannot be empty");

        }

        System.out.println("before add new user");
        User newUser = new User(userDTO.getFirstname(), userDTO.getLastname(),userDTO.getUsername(),userDTO.getPassword());
        System.out.println("After create new user");



        System.out.println("name   "+newUser.getUsername());

        System.out.println("pass   "+newUser.getPassword());
        System.out.println("fname  "+newUser.getFirstname());
        System.out.println("lname  " +newUser.getLastname());
        return userDAO.save(newUser);

    }

    //This service will facilitate login - get a user from the DAO (or null)
    public Optional<User> loginUser(IncomingUserDTO userDTO)
    {
        //TODO: validity check

        //if all checks pass, return a User OR null, and send it to the controller
        return userDAO.findByUsernameAndPassword(userDTO.getUsername(),userDTO.getPassword());
    }

}
