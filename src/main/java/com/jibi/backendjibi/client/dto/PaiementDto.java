package com.jibi.backendjibi.client.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class PaiementDto {
    private Long id;
    private String phone;
    private double amount;
    private String image;
}
