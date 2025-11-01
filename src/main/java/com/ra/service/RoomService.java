package com.ra.service;

import com.ra.model.dto.request.RoomDTO;
import com.ra.model.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    Room addRoom(RoomDTO roomDTO);

    void save(Room room);

    Room findRoomById(Long id);

    Room updateRoom(Long id,RoomDTO roomDTO);

    String deleteRoom(Long id);

    Page<Room> getAllRooms(Pageable pageable);
}
