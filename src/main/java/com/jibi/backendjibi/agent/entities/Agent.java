package com.jibi.backendjibi.agent.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter
@Setter
@Data
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String photo;
}
