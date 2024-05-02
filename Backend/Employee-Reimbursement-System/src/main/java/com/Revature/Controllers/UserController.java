package com.Revature.Controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.Revature.Models.DTOs.IncomingUserDTO;
import com.Revature.Models.DTOs.OutgoingUserDTO;
import com.Revature.Models.User;
import com.Revature.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin( origins =  "http://localhost:3000")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        System.out.println("inside user controller constructure");
        this.userService = userService;
    }
@PostMapping
        public ResponseEntity<String> addUser(@RequestBody IncomingUserDTO userDTO){
        System.out.println(" indise the addUser in Controller layer");
    System.out.println("name   "+userDTO.getUsername());

    System.out.println("pass   "+userDTO.getPassword());
    System.out.println("fname  "+userDTO.getFirstname());
    System.out.println("lname  " +userDTO.getLastname());

        try {
            userService.addUser(userDTO);
            return ResponseEntity.status(201).body(userDTO.getFirstname() + userDTO.getLastname()+"with"+userDTO.getUsername()+"has sucessfully added");

        }catch(IllegalArgumentException  e )
        {
            return  ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity <?> loginUser(@RequestBody IncomingUserDTO userDTO, HttpSession session){

        //Get the User object from the service (which talks to the DB)
        Optional<User> optionalUser=userService.loginUser(userDTO);

        //If login fails (which will return an empty optional), tell the user they failed
        if(optionalUser.isEmpty())
        {
            return ResponseEntity.status(401).body("Login faild : Invaild User Creditial");

        }
       User u= optionalUser.get();
        //Storing the user info in our session
        session.setAttribute("userId",u.getUserId());
        session.setAttribute("username",u.getUsername());//probably won't use this


        //Hypothetical role save to session
        //session.setAttribute("role", u.getRole());

        //Finally, send back a 200 (OK) as well as a OutgoingUserDTO

        return ResponseEntity.ok(new OutgoingUserDTO(u.getUserId(),u.getUsername(),u.getFirstname(),u.getLastname(),u.getRole()));

    }

}