package com.ra.repository;

import com.ra.model.entity.RoomServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomServiceRepository extends JpaRepository<RoomServices,Long> {
}
