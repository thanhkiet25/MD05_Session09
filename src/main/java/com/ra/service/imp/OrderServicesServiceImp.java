package com.ra.service.imp;

import com.ra.model.constant.Status;
import com.ra.model.dto.request.OrderServicesDTO;
import com.ra.model.entity.OrderServices;
import com.ra.model.entity.Reservation;
import com.ra.repository.OrderServicesRepository;
import com.ra.service.OrderServicesService;
import com.ra.service.ReservationServicesService;
import com.ra.service.RoomServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderServicesServiceImp implements OrderServicesService {
    @Autowired
    private RoomServicesService roomServicesService;
    @Autowired
    private OrderServicesRepository orderServiceRepository;
    @Autowired
    private ReservationServicesService reservationService;
    @Override
    public ResponseEntity<?> addOrderService(OrderServicesDTO orderServicesDTO) {
        Reservation reservation = reservationService.findById(orderServicesDTO.getReservationId());
        if (reservation != null ){
            if (reservation.getStatus() == Status.CONFIRMED){
                OrderServices orderServices = new OrderServices();
                orderServices.setRoomServices(roomServicesService.findById(orderServicesDTO.getRoomServicesId()));
                orderServices.setReservation(reservationService.findById(orderServicesDTO.getReservationId()));
                orderServices.setCreatedDate(LocalDateTime.now());
                orderServices.setQuantity(orderServicesDTO.getQuantity());
                try {
                    return new ResponseEntity<>(orderServiceRepository.save(orderServices), HttpStatus.CREATED);
                }catch (Exception e){
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                return new ResponseEntity<>("room is pending or cancel can not order service !",HttpStatus.BAD_REQUEST);
            }

        }else {
            return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
        }

    }


    @Override
    public OrderServices findById(long id) {
        return orderServiceRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> editServiceOrder(Long id, OrderServicesDTO orderServicesDTO) {
        OrderServices orderServices = findById(id);
        if (orderServices != null) {
            orderServices.setRoomServices(roomServicesService.findById(orderServicesDTO.getRoomServicesId()));
            orderServices.setReservation(reservationService.findById(orderServicesDTO.getReservationId()));
            orderServices.setQuantity(orderServicesDTO.getQuantity());
            try {
                return new ResponseEntity<>(orderServiceRepository.save(orderServices),HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>("Order service not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<OrderServices> findAllByReservationId(long reservationId) {
        return orderServiceRepository.findByReservationId(reservationId);
    }

    @Override
    public ResponseEntity<?> getAllServicesByReservationId(Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);
        if (reservation != null) {
            try {
                return new ResponseEntity<>(orderServiceRepository.findByReservationId(reservationId),HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
        }

    }
    }

