package com.ra.service;

import com.ra.model.entity.Reservation;
import com.ra.security.CustomerPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface ReservationServicesService {
    ResponseEntity<?> bookRoom(long roomId , @AuthenticationPrincipal CustomerPrincipal customerPrincipal);

    Reservation findById(long id);

    ResponseEntity<String> cancelReservation(Long id);

    Page<Reservation> getAllReservations(Pageable pageable);

    void save(Reservation reservation);

    ResponseEntity<String> confirmReservation(Long id);
}
