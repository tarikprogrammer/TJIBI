package com.jibi.backendjibi.client.dto;

import com.jibi.backendjibi.cmi.entities.Compte;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Data
public class ClientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    private String cin;
    private String address;
    private String account;
    private String password;
    private Compte compte;
    private String photo;
    private List<TransactionDto> transactionDto;
    private List<PaiementDto> paiementDto;
}
