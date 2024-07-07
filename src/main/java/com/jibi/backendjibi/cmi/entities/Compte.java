package com.jibi.backendjibi.cmi.entities;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jibi.backendjibi.client.entities.ClientEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rib;
    private double balance;
    private double plafond;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name="client_id")
    private ClientEntity client;
    @Column(name="client_id",insertable = false,updatable = false)
    private long idClient;

}
