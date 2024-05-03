package com.Revature.Controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.Revature.DAOs.UserDAO;
import com.Revature.Models.DTOs.IncomingUserDTO;
import com.Revature.Models.DTOs.OutgoingUserDTO;
import com.Revature.Models.User;
import com.Revature.Services.ReimbService;
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
        System.out.println(" indise the addUser in Controller layer");System.out.println("name   "+userDTO.getUsername());

    System.out.println("pass   "+userDTO.getPassword());
    System.out.println("fname  "+userDTO.getFirstname());
    System.out.println("lname  " +userDTO.getLastname());
    System.out.println("role  " +userDTO.getRole());

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
        session.setAttribute("role", u.getRole());
        session.setAttribute("username",u.getUsername());//probably won't use this


        //Hypothetical role save to session
        //session.setAttribute("role", u.getRole());

        //Finally, send back a 200 (OK) as well as a OutgoingUserDTO

        return ResponseEntity.ok(new OutgoingUserDTO(u.getUserId(),u.getUsername(),u.getFirstname(),u.getLastname(),u.getRole()));

    }

    // Get users (allow manager only)
    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpSession session)
    {
        //UserDAO userDAO ;

        System.out.println((" current user id " +session.getAttribute("userId")));
       // int userId = (int) session.getAttribute("userId");
        //Optional<User> user= userDAO.findById(userId);

       //User u = user.get();
       //String role = u.getRole();

       //System.out.println("role from user " + role);
        //Login check
        if((session.getAttribute("userId") == null )){
            return ResponseEntity.status(401).body("You must be logged  as Manager");
        }

        System.out.println("check role of current signed user  "+session.getAttribute("role"));
       if(!session.getAttribute("role").equals("manager")){
            return ResponseEntity.status(401).body("You must be logged  as Manager");
        }
        System.out.println("Lets try manager role");
        /*if((role.contains("manger") ) ){
            return ResponseEntity.status(401).body("You must be logged in  as manager");
        }*/


        //Get the userId from the session
       int userId = (int) session.getAttribute("userId");

        return ResponseEntity.ok(userService.getAllUsers());

    }

    //delete a pokemon by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId,HttpSession session)
    {
        if((session.getAttribute("userId") == null ) ){
            return ResponseEntity.status(401).body("You must be logged ");
        }

        System.out.println("check role of current signed user  "+session.getAttribute("role"));
        if(!session.getAttribute("role").equals("manager")){
            return ResponseEntity.status(401).body("You must be logged  as Manager");
        }

        try{
            userService.deleteuser(userId);
            return ResponseEntity.ok("User is deleted");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
