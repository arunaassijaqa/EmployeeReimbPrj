package com.Revature.Controllers;

import com.Revature.Models.DTOs.IncomingReimbDTO;
import com.Revature.Models.Reimbursment;
import com.Revature.Services.ReimbService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReimbController {

    private ReimbService reimbService;

    @Autowired
    public ReimbController(ReimbService reimbService) {
        this.reimbService = reimbService;
    }

    //post mapping for inserting new Reimbursement
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
                r.getUser().getUsername() + " Added  Reimbursement  with " + r.getReimbId()
                ));

    }


    // Get all Reimbursements
@GetMapping
    public ResponseEntity<?> getAllReimbursements(HttpSession session){

        //Login check

        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("You must be logged in to see your Reimbursements!");
        }
        //Get the userId from the session

        int userId =(int)session.getAttribute("userId");

        return ResponseEntity.ok((reimbService.getAllReimbursements(userId)));

    }

}
