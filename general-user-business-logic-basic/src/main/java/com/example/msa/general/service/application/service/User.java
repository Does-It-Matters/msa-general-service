package com.example.msa.general.service.application.service;

import com.example.msa.general.service.application.port.in.GeneralUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User implements GeneralUser {
    private String id;
    private String email;
    private int age;
}