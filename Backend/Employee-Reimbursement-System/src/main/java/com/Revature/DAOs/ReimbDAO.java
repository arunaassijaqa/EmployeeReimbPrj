package com.Revature.DAOs;

import com.Revature.Models.Reimbursment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbDAO extends JpaRepository<Reimbursment,Integer> {

    public List<Reimbursment> findByUserUserId(int userId);

    public List<Reimbursment> findAllByStatus(String status);


}
