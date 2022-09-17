package com.brugalibre.visitorparking.persistence.facility.mapper;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import com.brugalibre.visitorparking.persistence.resident.mapper.ResidentEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface FacilityEntityOnlyMapper {
   /**
    * Due to cyclic dependencies between the {@link Facility} and the {@link Resident} we can't include the {@link FacilityEntityMapper} into the {@link ResidentEntityMapper}
    *
    * @param facilityEntity the {@link FacilityEntity} to map
    * @return a mapped {@link Facility}
    */
   @Mapping(target = "residents", ignore = true)
   Facility map2FacilityOnly(FacilityEntity facilityEntity);
}