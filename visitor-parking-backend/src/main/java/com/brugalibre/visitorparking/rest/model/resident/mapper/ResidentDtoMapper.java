package com.brugalibre.visitorparking.rest.model.resident.mapper;

import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.rest.model.resident.ResidentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = VisitorParkingCardDtoMapper.class)
public interface ResidentDtoMapper {
   /**
    * Maps from a {@link Resident} into a {@link ResidentDto}
    *
    * @param resident the {@link Resident} to map
    * @param user     the user which belongs to this {@link Resident}
    * @return an instance of a mapped {@link ResidentDto}
    */
   @Mapping(target = "facilityId", source = "resident.facility.id")
   @Mapping(target = "name", source = "user.username")
   @Mapping(target = "id", source = "resident.id")
   ResidentDto map2ResidentDto(Resident resident, User user);

}