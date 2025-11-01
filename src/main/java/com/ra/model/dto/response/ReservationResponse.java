package com.ra.model.dto.response;

import com.ra.model.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReservationResponse {
    private Long id;
    private String customerName;
    private String roomName;
    private Status status;
}