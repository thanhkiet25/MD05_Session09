package com.ra.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderServicesDTO {
    private long reservationId;
    private long roomServicesId;
    private int quantity;
}
