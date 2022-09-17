package com.brugalibre.visitorparking.persistence.facility.mapper;

import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import com.brugalibre.visitorparking.persistence.parking.mapper.ParkingLotEntityMapper;
import com.brugalibre.visitorparking.persistence.resident.mapper.ResidentEntityMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {ParkingLotEntityMapper.class, ResidentEntityMapper.class})
public interface FacilityEntityMapper extends CommonDomainModelMapper<Facility, FacilityEntity> {
   // no-op
}