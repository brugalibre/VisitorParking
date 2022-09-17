package com.brugalibre.visitorparking.persistence.parking;


import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import org.springframework.data.repository.CrudRepository;

public interface ParkedCarDao extends CrudRepository<ParkedCarEntity, String> {
   /**
    * Returns a {@link ParkedCar} by its car plate number
    *
    * @param carPlateNo the car plate number
    * @return a {@link ParkedCar}
    */
   ParkedCarEntity getByCarPlateNo(String carPlateNo);
}
