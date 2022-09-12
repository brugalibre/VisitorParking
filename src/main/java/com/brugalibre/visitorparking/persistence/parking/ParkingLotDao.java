package com.brugalibre.visitorparking.persistence.parking;


import org.springframework.data.repository.CrudRepository;

public interface ParkingLotDao extends CrudRepository<ParkingLotEntity, String> {
   // no-op
}
