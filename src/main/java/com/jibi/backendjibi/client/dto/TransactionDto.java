package com.jibi.backendjibi.client.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class TransactionDto {
    private Long id;
    private String ribTo;
    private double amount;
    private String ribFrom;
    private String sender;
    private String receiver;
}
