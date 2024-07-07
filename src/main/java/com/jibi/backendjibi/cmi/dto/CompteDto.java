package com.jibi.backendjibi.cmi.dto;

import com.jibi.backendjibi.client.entities.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompteDto {
    private Long id;
    private String rib;
    private double balance;
    private double plafond;
    private ClientEntity client;
}
