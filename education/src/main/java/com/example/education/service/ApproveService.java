package com.example.education.service;

import com.example.education.repository.ApproveRepository;
import com.example.education.user.Approve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ASUS
 */
@Service
public class ApproveService {
    @Autowired
    ApproveRepository approveRepository;

    public Approve select(String deleteNumber, String addNumber,String studentNumber) {
        return approveRepository.findByDeleteNumberAndAddNumberAndStudentNumber(deleteNumber, addNumber,studentNumber);
    }

    public void update(String deleteNumber, String addNumber, String studentNumber, String state, String operator) {
        Approve approve = approveRepository.findByDeleteNumberAndAddNumberAndStudentNumber(deleteNumber, addNumber,studentNumber);
        approveRepository.update(approve.getId(),state);
        approveRepository.updateOperator(approve.getId(),operator);
    }

    public int save(Approve approve) {
        approveRepository.save(approve);
        return 1;
    }

    public List<Approve> selectByStudentNumber(String studentNumber) {
        return approveRepository.findByStudentNumber(studentNumber);
    }

    public List<Approve> selectAll() {
        return approveRepository.findAll();
    }

    public List<Approve> selectByState(String state) {
        return approveRepository.findByState(state);
    }
}
