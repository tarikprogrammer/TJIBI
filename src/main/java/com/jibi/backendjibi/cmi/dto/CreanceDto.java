package com.jibi.backendjibi.cmi.dto;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter
@Setter
@Data
public class CreanceDto {
    private Long id;
    private String imageUrl;
    private String country;
    private String network;
}
