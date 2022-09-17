package com.brugalibre.visitorparking.persistence.parking.mapper;

import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.persistence.parking.ParkedCarEntity;
import com.brugalibre.visitorparking.persistence.parking.ParkingLotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SimpleParkingLotEntityMapper {

   /**
    * Maps from a {@link ParkingLotEntity} to a {@link ParkingLot} but don't map all other values from {@link ParkingLotEntity}
    * Due to cyclic dependencies between {@link ParkedCarEntity} and {@link ParkingLot} the {@link ParkingLotEntityMapper} can't include the
    * {@link ParkingLotEntityMapper}.
    * <p>
    * So that's why it can include this one, but this one does not map the resident itself and avoids therefore a cyclic dependency
    *
    * @param parkingLotEntity the {@link ParkingLot} to map
    * @return a mapped ParkingLot
    */
   @Mapping(target = "parkedCars", ignore = true)
   ParkingLot map2ParkingLotOnly(ParkingLotEntity parkingLotEntity);
}