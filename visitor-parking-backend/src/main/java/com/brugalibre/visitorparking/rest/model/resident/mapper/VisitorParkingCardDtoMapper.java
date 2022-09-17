package com.brugalibre.visitorparking.rest.model.resident.mapper;

import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.rest.model.resident.VisitorParkingCardDto;
import org.mapstruct.Mapper;

@Mapper
public interface VisitorParkingCardDtoMapper {

   /**
    * Maps from a {@link VisitorParkingCard} into a {@link VisitorParkingCardDto}
    *
    * @param visitorParkingCard the {@link VisitorParkingCard} to map
    * @return an instance of a mapped {@link VisitorParkingCardDto}
    */
   VisitorParkingCardDto map2VisitorParkingCardDto(VisitorParkingCard visitorParkingCard);
}