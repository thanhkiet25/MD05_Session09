package com.ra.service;

import com.ra.model.dto.request.OrderServicesDTO;
import com.ra.model.entity.OrderServices;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderServicesService {

    ResponseEntity<?> addOrderService(OrderServicesDTO orderServicesDTO);

    OrderServices findById(long id);

    ResponseEntity<?> editServiceOrder(Long id, OrderServicesDTO orderServicesDTO);

    List<OrderServices> findAllByReservationId(long reservationId);

    ResponseEntity<?> getAllServicesByReservationId(Long reservationId);
}
