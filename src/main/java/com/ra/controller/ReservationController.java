package com.ra.controller;

import com.ra.model.entity.Reservation;
import com.ra.security.CustomerPrincipal;
import com.ra.service.ReservationServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationServicesService reservationService;

    @GetMapping
    public ResponseEntity<Page<Reservation>> getReservations(@RequestParam(name = "page" , defaultValue = "0") int page,
                                                             @RequestParam(name = "size",defaultValue = "5") int size) {
        return new ResponseEntity<>(reservationService.getAllReservations(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookReservation(@RequestParam("roomId") long roomId , @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        return reservationService.bookRoom(roomId, customerPrincipal);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable long id) {
        return reservationService.cancelReservation(id);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<String> confirmReservation(@PathVariable long id) {
        return reservationService.confirmReservation(id);
    }
}
