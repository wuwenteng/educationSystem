package com.example.education.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Result<T> {
    private String code;
    private String message;
    private User user;
    private List<T> data = new ArrayList<>();
}
