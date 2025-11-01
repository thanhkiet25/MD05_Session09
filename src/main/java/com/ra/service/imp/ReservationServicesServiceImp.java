package com.ra.service.imp;

import com.ra.model.constant.Status;
import com.ra.model.dto.response.ReservationResponse;
import com.ra.model.entity.Reservation;
import com.ra.model.entity.Room;
import com.ra.repository.ReservationRepository;
import com.ra.security.CustomerPrincipal;
import com.ra.service.ReservationServicesService;
import com.ra.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class ReservationServicesServiceImp implements ReservationServicesService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomService roomService;
    @Override
    public ResponseEntity<?> bookRoom(long roomId , @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        Room room = roomService.findRoomById(roomId);
        if(room != null && room.getReservation() != null) {
            return new ResponseEntity<>("Reservation is already booked", HttpStatus.BAD_REQUEST) ;
        }
        Reservation reservation = new Reservation();
        reservation.setCustomer(customerPrincipal.customer);
        reservation.setRoom(room);
        reservation.setStatus(Status.PENDING);
        try {
            Reservation newReservation = reservationRepository.save(reservation);
            room.setReservation(newReservation);
            roomService.save(room);
            ReservationResponse reservationResponse = ReservationResponse
                    .builder()
                    .customerName(newReservation.getCustomer().getName())
                    .roomName(newReservation.getRoom().getRoomName())
                    .status(newReservation.getStatus())
                    .id(newReservation.getId())
                    .build();
            return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
    }


    @Override
    public Reservation findById(long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<String> cancelReservation(Long id) {
        Reservation reservation = findById(id);

        if (reservation != null ) {
            if (reservation.getStatus().equals(Status.PENDING)){
                reservation.setStatus(Status.CANCELLED);
                try {
                    reservationRepository.save(reservation);
                    Room room = reservation.getRoom();
                    room.setReservation(null);
                    roomService.save(room); // Trả lại phòng
                    return new ResponseEntity<>("Reservation cancelled", HttpStatus.OK);
                }catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
                }
            }else {
                return new ResponseEntity<>("Can not cancel reservation when status is cancel or confirm !", HttpStatus.BAD_REQUEST) ;
            }

        }else {
            return new ResponseEntity<>("Reservation not found !", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public ResponseEntity<String> confirmReservation(Long id) {
        Reservation reservation = findById(id);
        if (reservation != null){
            if(reservation.getStatus().equals(Status.PENDING)){
                reservation.setStatus(Status.CONFIRMED);
                try {
                    reservationRepository.save(reservation);
                    return new ResponseEntity<>("Confirm reservation successfully !", HttpStatus.OK);
                }catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
                }
            }else {
                return new ResponseEntity<>("Can not confirm reservation when status is cancel or confirm !", HttpStatus.BAD_REQUEST) ;
            }

        }else {
            return new ResponseEntity<>("Reservation not found !", HttpStatus.NOT_FOUND);
        }

    }
}
