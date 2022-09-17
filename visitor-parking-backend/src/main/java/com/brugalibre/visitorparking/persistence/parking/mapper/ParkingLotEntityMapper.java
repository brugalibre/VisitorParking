package com.brugalibre.visitorparking.persistence.parking.mapper;

import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.persistence.parking.ParkingLotEntity;
import com.brugalibre.visitorparking.persistence.resident.mapper.VisitorParkingCardEntityMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {ParkedCarEntityMapper.class, VisitorParkingCardEntityMapper.class})
public interface ParkingLotEntityMapper extends CommonDomainModelMapper<ParkingLot, ParkingLotEntity> {

   @AfterMapping
   default void setParkingLotEntityOnParkedCars(@MappingTarget ParkingLotEntity parkingLot) {
      parkingLot.getParkedCars()
              .forEach(parkedCarEntity -> parkedCarEntity.setParkingLot(parkingLot));
   }
}