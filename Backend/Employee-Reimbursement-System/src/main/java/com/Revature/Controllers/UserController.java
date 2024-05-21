package com.Revature.Controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.Revature.DAOs.UserDAO;
import com.Revature.Models.DTOs.IncomingUserDTO;
import com.Revature.Models.DTOs.OutgoingUserDTO;
import com.Revature.Models.User;
import com.Revature.Services.ReimbService;
import com.Revature.Services.UserService;
import com.Revature.Utils.JwtTokenUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    private UserService userService;

    private AuthenticationManager authManager;
    private JwtTokenUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    //SPRING SECURITY - lets us encode our passwords

    @Autowired
    public UserController(UserService userService, AuthenticationManager authManager, JwtTokenUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }
    /*@Autowired
    public UserController(UserService userService) {
        System.out.println("inside user controller constructure");
        this.userService = userService;
    }*/

    // U1.Employee: Create an account(create new user)
@PostMapping
public ResponseEntity<?> addUser(@RequestBody IncomingUserDTO userDTO){

    System.out.println(" inside the addUser in Controller layer");

    System.out.println("name   "+userDTO.getUsername());

    System.out.println("pass   "+userDTO.getPassword());
    System.out.println("fname  "+userDTO.getFirstname());
    System.out.println("lname  " +userDTO.getLastname());
    System.out.println("role  " +userDTO.getRole());

    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        try {
            userService.addUser(userDTO);
            return ResponseEntity.status(201).body(userDTO);

        }catch(Exception   e )
        {
            return  ResponseEntity.status(400).body(e.getMessage());
        }

    }

    /*// Login user(manager/employee)
    @PostMapping("/login")
    public ResponseEntity <?> loginUser(@RequestBody IncomingUserDTO userDTO, HttpSession session){

        //Get the User object from the service (which talks to the DB)
        Optional<User> optionalUser=userService.loginUser(userDTO);
        System.out.println("Inside login method");
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

    }*/// Login user(manager/employee)
    @PostMapping("/login")
    public ResponseEntity <?> loginUser(@RequestBody IncomingUserDTO userDTO){

        //attempt to login (notice no direct calls of the service/DAO)
        try{
            //this is what authenticates the incoming username/password
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );

            //build up the user based on the validation
            User u = (User) auth.getPrincipal();

            //if the user is found/valid, generate a JWT!
            String accessToken = jwtUtil.generateAccessToken(u);

            //create our OutgoingUserDTO to send back (which will include, ID, Username, and JWT)
            OutgoingUserDTO userOut = new OutgoingUserDTO(u.getUserId(),u.getUsername(),u.getFirstname(),u.getLastname(),u.getRole() , accessToken) ;


            //finally we sent the OutgoingUserDTO back to the client
            return ResponseEntity.ok(userOut);

        } catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }



    }



    /*// M4.Get users (allow manager only)
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


        //Get the userId from the session
       int userId = (int) session.getAttribute("userId");

        return ResponseEntity.ok(userService.getAllUsers());

    }*/

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token)
    {
        //UserDAO userDAO ;

        String jwt = token.substring(7);

        int userId = jwtUtil.extractUserId(jwt);
        String username = jwtUtil.extractUsername(jwt);
        String role = jwtUtil.extractRole(jwt);

        System.out.println(" current user id " +userId);



        System.out.println("check role of current signed user  "+ role);
        if(!role.equals("manager")){
            return ResponseEntity.status(401).body("You must be logged  as Manager");
        }




        return ResponseEntity.ok(userService.getAllUsers());

    }


    //M5:delete a User  by ID
    /*@DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId,HttpSession session)
    {
        if((session.getAttribute("userId") == null ) )
        {
            return ResponseEntity.status(401).body("You must be logged ");
        }

        if(((int)session.getAttribute("userId") == userId ) )
        {
            return ResponseEntity.status(401).body("User Can't delete him/herself ");
        }

        System.out.println("check role of current signed user  "+session.getAttribute("role"));
        if(!session.getAttribute("role").equals("manager"))
        {
            return ResponseEntity.status(401).body("You must be logged  as Manager");
        }

        try
        {
            if (userService.deleteuser(userId))
            {
                return ResponseEntity.ok("User " + userId+" is deleted");
            } else
            {
                return ResponseEntity.badRequest().body("User not found");
            }

        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }*/

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId,@RequestHeader("Authorization") String token)
    {

        String jwt = token.substring(7);

        int jwtuserId = jwtUtil.extractUserId(jwt);
        String username = jwtUtil.extractUsername(jwt);
        String role = jwtUtil.extractRole(jwt);



        if(jwtuserId == userId )
        {
            return ResponseEntity.status(401).body("User Can't delete him/herself ");
        }

        System.out.println("check role of current signed user  "+role);
        if(!role.equals("manager"))
        {
            return ResponseEntity.status(401).body("You must be logged  as Manager");
        }

        try
        {
            if (userService.deleteuser(userId))
            {
                return ResponseEntity.ok("User " + userId+" is deleted");
            } else
            {
                return ResponseEntity.badRequest().body("User not found");
            }

        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
