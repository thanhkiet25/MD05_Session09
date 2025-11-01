package com.ra.service.imp;

import com.ra.model.dto.request.RoomDTO;
import com.ra.model.entity.Room;
import com.ra.repository.RoomRepository;
import com.ra.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImp implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public Room addRoom(RoomDTO roomDTO) {
        Room newRoom = convertRoomDTOToRoom(roomDTO);
        newRoom.setReservation(null);
        try {
            return roomRepository.save(newRoom);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room updateRoom(Long id, RoomDTO roomDTO) {
        Room oldRoom = findRoomById(id);
        if (oldRoom != null) {
            oldRoom.setRoomName(roomDTO.getRoomName());
            oldRoom.setType(roomDTO.getType());
            oldRoom.setPrice(roomDTO.getPrice());
            try {
                return roomRepository.save(oldRoom);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public String deleteRoom(Long id) {
        Room room = findRoomById(id);
        if (room != null) {
            try {
                roomRepository.delete(room);
                return "Delete room successfully";
            } catch (Exception e) {
                return "Delete Room Failed";
            }
        }else {
            return "Room not found";
        }
    }

    @Override
    public Page<Room> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }
    public Room convertRoomDTOToRoom(RoomDTO roomDTO) {
        return Room
                .builder()
                .roomName(roomDTO.getRoomName())
                .type(roomDTO.getType())
                .price(roomDTO.getPrice())
                .build();
    }
}
