package com.ra.service;

import com.ra.model.dto.request.RoomServiceDTO;
import com.ra.model.entity.RoomServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RoomServicesService {
    RoomServices addService(RoomServiceDTO roomServiceDTO);

    RoomServices findById(Long id);

    RoomServices updateService(Long id,RoomServiceDTO roomServiceDTO);

    ResponseEntity<String> deleteService(Long id);

    Page<RoomServices> getAllServices(Pageable pageable);
}
