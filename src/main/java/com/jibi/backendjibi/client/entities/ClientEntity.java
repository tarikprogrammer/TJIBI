package com.jibi.backendjibi.client.entities;

import com.jibi.backendjibi.cmi.entities.Compte;
import com.jibi.backendjibi.cmi.entities.Paiement;
import com.jibi.backendjibi.cmi.entities.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="client")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Data
public class ClientEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String photo;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "client")
    private Compte compte;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "client")
    private List<Transaction>transactions;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "client")
    private List<Paiement>paiements;
}
