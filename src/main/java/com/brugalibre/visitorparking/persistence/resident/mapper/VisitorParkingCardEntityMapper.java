package com.brugalibre.visitorparking.persistence.resident.mapper;

import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.persistence.resident.VisitorParkingCardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VisitorParkingCardEntityMapper {

   VisitorParkingCardEntity map2VisitorParkingCardEntity(VisitorParkingCard visitorParkingCard);

   @Mapping(target = "assignToParkedCar", ignore = true)
   VisitorParkingCard map2VisitorParkingCard(VisitorParkingCardEntity visitorParkingCardEntity);
}