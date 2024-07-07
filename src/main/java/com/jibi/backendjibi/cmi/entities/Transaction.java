package com.jibi.backendjibi.cmi.entities;

import com.jibi.backendjibi.client.entities.ClientEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ribTo;
    private double amount;
    private String ribFrom;
    private String sender;
    private String receiver;
    @ManyToOne
    @JoinColumn(name="client_id")
    private ClientEntity client;
    @Column(name="client_id",updatable = false,insertable = false)
    private Long clientSender;
    @Column(name="client_id",updatable = false,insertable = false)
    private Long clientReceiver;

}
