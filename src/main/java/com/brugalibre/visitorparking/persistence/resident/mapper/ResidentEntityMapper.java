package com.brugalibre.visitorparking.persistence.resident.mapper;

import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import com.brugalibre.visitorparking.persistence.facility.mapper.FacilityEntityMapper;
import com.brugalibre.visitorparking.persistence.resident.ResidentEntity;
import com.brugalibre.visitorparking.persistence.user.mapper.UserEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {VisitorParkingCardEntityMapper.class, UserEntityMapper.class})
public interface ResidentEntityMapper extends CommonDomainModelMapper<Resident, ResidentEntity> {
   /**
    * Due to cyclic dependencies between the {@link Facility} and the {@link Resident} we can't include the {@link FacilityEntityMapper}
    *
    * @param facilityEntity the {@link FacilityEntity} to map
    * @return a mapped {@link Facility}
    */
   @Mapping(target = "residents", ignore = true)
   @Mapping(target = "removeParkedCar", ignore = true)
   @Mapping(target = "parkingLot.removeParkedCarByPlateNo", ignore = true)
   Facility map2FacilityOnly(FacilityEntity facilityEntity);
}