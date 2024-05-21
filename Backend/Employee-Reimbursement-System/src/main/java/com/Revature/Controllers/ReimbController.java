package com.Revature.Controllers;

import com.Revature.Models.DTOs.IncomingReimbDTO;
import com.Revature.Models.Reimbursment;
import com.Revature.Models.User;
import com.Revature.Services.ReimbService;
import com.Revature.Utils.JwtTokenUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReimbController {

    private ReimbService reimbService;

    private JwtTokenUtil JwtUtil;

  /*  @Autowired
    public ReimbController(ReimbService reimbService) {
        this.reimbService = reimbService;
    }*/

    @Autowired
    public ReimbController(ReimbService reimbService, JwtTokenUtil jwtUtil) {
        this.reimbService = reimbService;
        JwtUtil = jwtUtil;
    }

    /*//post mapping for inserting new Reimbursement
    //U2: Create a new Reimbursement
    @PostMapping
    public ResponseEntity<String> addReimb(@RequestBody IncomingReimbDTO reimbDTO, HttpSession session){

        System.out.println(" Inside add Reimbursement controller method  with session id " + session.getId());
        //If the user is not logged in (if the userId is null), send back a 401
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("You must be logged in to add the reimbursment");
        }

        //Now that we have user info saved (in our HTTP Session), we can attach the stored user Id to the reimbDTO
        reimbDTO.setUserId((int) session.getAttribute("userId"));
        //why do we need to cast to an int? getAttribute returns an Object

        //TODO: try/catch once we decide to do some error handling

        Reimbursment r = reimbService.addReimb(reimbDTO);

        return  ResponseEntity.status(201).body((
                r.getUser().getUsername() + " Added Reimbursement with Reimbursement ID : " + r.getReimbId()
                ));

    }*/
    @PostMapping
    public ResponseEntity<String> addReimb(@RequestBody IncomingReimbDTO reimbDTO, @RequestHeader("Authorization") String token) {

        System.out.println(" Inside addReimb");

        String jwt = token.substring(7);
        int userId = JwtUtil.extractUserId(jwt);
        String username = JwtUtil.extractUsername(jwt);
        String role = JwtUtil.extractRole(jwt);

        System.out.println(" Inside add Reimbursement controller method  with user id " + userId + " " + username + " " + role);


        //Now that we have user info saved (in our HTTP Session), we can attach the stored user Id to the reimbDTO
        reimbDTO.setUserId(userId);
        //why do we need to cast to an int? getAttribute returns an Object

        //TODO: try/catch once we decide to do some error handling

        Reimbursment r = reimbService.addReimb(reimbDTO);

        return ResponseEntity.status(201).body((
                r.getUser().getUsername() + " Added Reimbursement with Reimbursement ID : " + r.getReimbId()
        ));

    }



/*//U3: see all Reimbursement tickets(only their own)
    // Get all Reimbursements by user id
@GetMapping
    public ResponseEntity<?> getAllReimbursements(HttpSession session){

        //Login check

        if(session.getAttribute("userId") == null ){
            return ResponseEntity.status(401).body("You must be logged in to see your Reimbursements!");
        }

    System.out.println(" Logged in user Id : " + session.getAttribute("userId"));
    if (session.getAttribute("role").equals("employee") || session.getAttribute("role").equals("Employee"))
    {

        System.out.println(" inside employee ");
        int userId =(int)session.getAttribute("userId");

        return ResponseEntity.ok(reimbService.getAllReimbursementsByUserId(userId));
    }
    else {
        return ResponseEntity.ok(reimbService.getAllReimbursements());
    }*/

    @GetMapping
    public ResponseEntity<?> getAllReimbursements(@RequestHeader("Authorization") String token) {

        System.out.println("Inside getAllReimbursements ");

        String jwt = token.substring(7);
        int userId = JwtUtil.extractUserId(jwt);
        String username = JwtUtil.extractUsername(jwt);
        String role = JwtUtil.extractRole(jwt);


        if (role.equals("employee") || role.equals("Employee")) {

            System.out.println(" inside employee ");


            return ResponseEntity.ok(reimbService.getAllReimbursementsByUserId(userId));
        } else {
            return ResponseEntity.ok(reimbService.getAllReimbursements());
        }


    }

/*
// U4 see only their pending reimbursement tickets ( Employee sees his pending reimb only)
    // M2:See all Pending  (Manager sees all employee (including his own) pending reimb )
    // get all Reimbursements of particular user with status pending , accesible by manager only

    @GetMapping("/{status}")
    public ResponseEntity<?> getAllReimbursementsByStatus(@PathVariable String status, HttpSession session){

       int uId = (int) session.getAttribute("userId");
        //Login check
        System.out.println(" inside getAllReimbursementsByStatus  with status " + status);
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("You must be logged in to see your Reimbursements!");
        }

        System.out.println("check role of current signed user  "+session.getAttribute("role"));
        System.out.println(" Logged in user Id : " + session.getAttribute("userId"));
        if (session.getAttribute("role").equals("employee") || session.getAttribute("role").equals("Employee"))
        {

            System.out.println(" inside employee ");
            return ResponseEntity.ok(reimbService.getAllReimbByUserIdAndStatus(uId,status));
        }
        else {
            System.out.println(" inside manger ");
            return ResponseEntity.ok((reimbService.getAllReimbByStatus(status)));

        }


    }*/

    @GetMapping("/{status}")
    public ResponseEntity<?> getAllReimbursementsByStatus(@PathVariable String status, @RequestHeader("Authorization") String token) {

        System.out.println("Inside getAllReimbursementsByStatus ");

        String jwt = token.substring(7);
        int userId = JwtUtil.extractUserId(jwt);
        String username = JwtUtil.extractUsername(jwt);
        String role = JwtUtil.extractRole(jwt);


        System.out.println("check role of current signed user  " + role);
        System.out.println(" Logged in user Id : " + userId);

        if (role.equals("employee") || role.equals("Employee")) {

            System.out.println(" inside employee ");
            return ResponseEntity.ok(reimbService.getAllReimbByUserIdAndStatus(userId, status));
        } else {
            System.out.println(" inside manger ");
            return ResponseEntity.ok((reimbService.getAllReimbByStatus(status)));

        }


    }
    /*// M3: Resolve a reimbursement ( Update status from pending to Approved or Denied)
    @PutMapping("/{reimbId}")
    public ResponseEntity<Object> UpdateReimbursmentStatus(@RequestBody IncomingReimbDTO reimbDTO ,@PathVariable int reimbId,HttpSession session)
    {
        //If the user is not logged in (if the userId is null), send back a 401
        if(session.getAttribute("userId") == null ){
            return ResponseEntity.status(401).body("You must be logged in to add the reimbursment");
        }
        System.out.println((" put:Inside the Update ReimbursmentStatus method "));

        Reimbursment r = reimbService.UpdateReimb(reimbDTO,reimbId);

        return ResponseEntity.ok(r);

    }*/

    @PutMapping("/{reimbId}")
    public ResponseEntity<Object> UpdateReimbursmentStatus(@RequestBody IncomingReimbDTO reimbDTO, @PathVariable int reimbId, @RequestHeader("Authorization") String token) {

        String jwt = token.substring(7);
        int userId = JwtUtil.extractUserId(jwt);
        String username = JwtUtil.extractUsername(jwt);
        String role = JwtUtil.extractRole(jwt);


        System.out.println((" put:Inside the Update ReimbursmentStatus method "));

        if (role.equals("manager") || role.equals("Manager")) {
            Reimbursment r = reimbService.UpdateReimb(reimbDTO, reimbId);
            return ResponseEntity.ok(r);
        }

        return ResponseEntity.status(401).body("Unathorized login");
    }

}
