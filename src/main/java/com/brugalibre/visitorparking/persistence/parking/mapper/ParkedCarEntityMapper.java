package com.brugalibre.visitorparking.persistence.parking.mapper;

import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.persistence.parking.ParkedCarEntity;
import com.brugalibre.visitorparking.persistence.resident.mapper.VisitorParkingCardEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {VisitorParkingCardEntityMapper.class, SimpleParkingLotEntityMapper.class})
public interface ParkedCarEntityMapper extends CommonDomainModelMapper<ParkedCar, ParkedCarEntity> {

   @Mapping(target = "assignVisitorParkingCard", ignore = true)
   @Mapping(target = "revokeVisitorParkingCard", ignore = true)
   @Override
   ParkedCar map2DomainModel(ParkedCarEntity domainEntity);

   @Mapping(target = "parkingLot", ignore = true)
   @Override
   ParkedCarEntity map2DomainEntity(ParkedCar domainModel);
}