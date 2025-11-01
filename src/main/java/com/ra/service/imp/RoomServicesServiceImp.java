package com.ra.service.imp;

import com.ra.model.dto.request.RoomServiceDTO;
import com.ra.model.entity.RoomServices;
import com.ra.repository.RoomServiceRepository;
import com.ra.service.RoomServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomServicesServiceImp implements RoomServicesService {
    @Autowired
    private RoomServiceRepository roomServiceRepository;

    @Override
    public RoomServices addService(RoomServiceDTO roomServiceDTO) {
        RoomServices roomServices = new RoomServices();
        roomServices.setName(roomServiceDTO.getName());
        roomServices.setPrice(roomServiceDTO.getPrice());
        try {
            return roomServiceRepository.save(roomServices);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RoomServices findById(Long id) {
        return roomServiceRepository.findById(id).orElse(null);
    }

    @Override
    public RoomServices updateService(Long id, RoomServiceDTO roomServiceDTO) {
        RoomServices roomServices = findById(id);
        if (roomServices != null) {
            roomServices.setName(roomServiceDTO.getName());
            roomServices.setPrice(roomServiceDTO.getPrice());
            try {
                return roomServiceRepository.save(roomServices);
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public ResponseEntity<String> deleteService(Long id) {
        RoomServices roomServices = findById(id);
        if (roomServices != null) {
            try {
                roomServiceRepository.delete(roomServices);
                return new ResponseEntity<>("Delete service successfully", HttpStatus.OK);
            }catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Delete service failed", HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("Service not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Page<RoomServices> getAllServices(Pageable pageable) {
        return roomServiceRepository.findAll(pageable);
    }
}
