package com.example.education.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String password;
    private  String role;
}
