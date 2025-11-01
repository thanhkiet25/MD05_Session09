package com.ra.repository;

import com.ra.model.entity.OrderServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderServicesRepository extends JpaRepository<OrderServices,Long> {
    List<OrderServices> findByReservationId(long reservationId);
}
