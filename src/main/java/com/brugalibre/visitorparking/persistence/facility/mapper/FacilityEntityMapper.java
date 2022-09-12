package com.brugalibre.visitorparking.persistence.facility.mapper;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import com.brugalibre.visitorparking.persistence.parking.mapper.ParkingLotEntityMapper;
import com.brugalibre.visitorparking.persistence.resident.mapper.ResidentEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ParkingLotEntityMapper.class, ResidentEntityMapper.class})
public interface FacilityEntityMapper extends CommonDomainModelMapper<Facility, FacilityEntity> {
   @Mapping(target = "removeParkedCar", ignore = true)
   @Override
   Facility map2DomainModel(FacilityEntity domainEntity);
}