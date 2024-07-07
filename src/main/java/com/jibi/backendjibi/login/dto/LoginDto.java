package com.jibi.backendjibi.login.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDto {
    private Long id;
    private String phone;
    private String password;
}
