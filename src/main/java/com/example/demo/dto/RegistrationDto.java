package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
