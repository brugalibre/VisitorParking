package com.brugalibre.visitorparking.rest.model.facility.mapper;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.rest.model.facility.FacilityDto;
import com.brugalibre.visitorparking.rest.model.resident.mapper.VisitorParkingCardDtoMapper;
import org.mapstruct.Mapper;

@Mapper(uses = VisitorParkingCardDtoMapper.class)
public interface FacilityDtoMapper {

   /**
    * Maps from a {@link Facility} into a {@link FacilityDto}
    *
    * @param facility the {@link Facility} to map
    * @return an instance of a mapped {@link FacilityDto}
    */
   FacilityDto map2FacilityDto(Facility facility);
}