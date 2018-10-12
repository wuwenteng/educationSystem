package com.example.education.service;

import com.example.education.user.User;
import com.example.education.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ASUS
 */
@Transactional
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    public void save(User user) {
        repository.save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public List<User> select(String name) {
        return repository.findByUsername(name);
    }

    public List<User> findByNameAndRole(String name, String role) {
        return repository.findByUsernameAndRole(name,role);
    }

    public void updatePassword(User user) {
        repository.update(user.getUsername(),user.getPassword());
    }
}
