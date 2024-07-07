package com.jibi.backendjibi.login.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ResponseDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String sexe;
    private String firstName;
    private String lastName;
    private String nationality;
    private String address;
    private String password;
    private boolean isAgent;
    private String cin;
    private String account;
}
