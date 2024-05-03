package com.Revature.Services;

import com.Revature.DAOs.ReimbDAO;
import com.Revature.DAOs.UserDAO;
import com.Revature.Models.DTOs.IncomingReimbDTO;
import com.Revature.Models.DTOs.OutgoingReimbDTO;
import com.Revature.Models.Reimbursment;
import com.Revature.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReimbService {

    private ReimbDAO reimbDAO;
    private UserDAO userDAO;

    @Autowired
    public ReimbService(ReimbDAO reimbDAO, UserDAO userDAO) {
        this.reimbDAO = reimbDAO;
        this.userDAO = userDAO;
    }
    //add pokemon to DB

    public Reimbursment addReimb(IncomingReimbDTO reimbDTO){

        //There aren't actual meaningful checks we can do on this
        //Because we assume valid poke data is coming from the pokeAPI (not user input)

        //We could check for things like valid poke name and image, valid userID
        //or maybe we can only allow each user to have x amount of pokemon
        //OR only one of each type of pokemon

        //But for now, we'll just insert a Pokemon

        //we need to get the User by id, and set it with the setter

        Reimbursment r = new Reimbursment(reimbDTO.getDescription(),reimbDTO.getAmount(),null);

        //Instantiate the appropriate user
        User u = userDAO.findById(reimbDTO.getUserId()).get() ;

        //Set the user in the Reimb object
        r.setUser(u);

        //now we can save the Reimb obect!
       // return  reimbDAO.save(r);
        return reimbDAO.save(r);
        //return r;

    }

    // get all Reimbursements
    public List<OutgoingReimbDTO> getAllReimbursementsByUserId(int userId)
    {
        //get all Reimbursements from the DB

        List<Reimbursment> allReimb = reimbDAO.findByUserUserId(userId);

        //for every reimbursement retrieved, we'll create a new OutgoingDTO
        //and add it to a List to be returned

        List<OutgoingReimbDTO> outReimb = new ArrayList<>();

        for(Reimbursment r : allReimb)
        {
            OutgoingReimbDTO outR= new OutgoingReimbDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getAmount(),
                    r.getStatus(),
                    r.getUser().getUserId());

            outReimb.add(outR);
        }
        return outReimb;
    }

    public List<OutgoingReimbDTO> getAllReimbursements()
    {
        //get all Reimbursements from the DB

        List<Reimbursment> allReimb = reimbDAO.findAll();

        //for every reimbursement retrieved, we'll create a new OutgoingDTO
        //and add it to a List to be returned

        List<OutgoingReimbDTO> outReimb = new ArrayList<>();

        for(Reimbursment r : allReimb)
        {
            OutgoingReimbDTO outR= new OutgoingReimbDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getAmount(),
                    r.getStatus(),
                    r.getUser().getUserId());

            outReimb.add(outR);
        }
        return outReimb;
    }

    public List<OutgoingReimbDTO> getAllReimbByStatus(String status)
    {
        System.out.println(" inside getAllReimbursementsByStatus  with status " + status);

        //get all Reimbursements from the DB

        List<Reimbursment> allReimb = reimbDAO.findAllByStatus(status);

        //for every reimbursement retrieved, we'll create a new OutgoingDTO
        //and add it to a List to be returned

        List<OutgoingReimbDTO> outReimb = new ArrayList<>();

        for(Reimbursment r : allReimb)
        {
            OutgoingReimbDTO outR= new OutgoingReimbDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getAmount(),
                    r.getStatus(),
                    r.getUser().getUserId());

            outReimb.add(outR);
        }
        return outReimb;

    }

    public List<OutgoingReimbDTO> getAllReimbByUserIdAndStatus(int userId,String status)
    {
        System.out.println(" inside getAllReimbursementsByUserIdAndStatus  with status " + status);

        //get all Reimbursements from the DB

        List<Reimbursment> allReimb = reimbDAO.findAllByUserUserIdAndStatus( userId, status);

        //for every reimbursement retrieved, we'll create a new OutgoingDTO
        //and add it to a List to be returned

        List<OutgoingReimbDTO> outReimb = new ArrayList<>();

        for(Reimbursment r : allReimb)
        {
            OutgoingReimbDTO outR= new OutgoingReimbDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getAmount(),
                    r.getStatus(),
                   r.getUser().getUserId());

            outReimb.add(outR);
        }
        return outReimb;

    }

  public  Reimbursment UpdateReimb(IncomingReimbDTO reimbDTO, int reimId){


System.out.println("Service: Inside UpdateReimb method ");
      Optional<Reimbursment> r = reimbDAO.findById(reimId);

      Reimbursment reimb = r.get();

      reimb.setStatus(reimbDTO.getStatus());
      reimb.setReimbId(reimId);
      reimb.setDescription(reimbDTO.getDescription());
      reimb.setAmount(reimbDTO.getAmount());



      return reimbDAO.save(reimb);



  }

}
