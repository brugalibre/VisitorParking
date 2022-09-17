package com.brugalibre.visitorparking.persistence.resident.mapper;

import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.persistence.resident.VisitorParkingCardEntity;
import org.mapstruct.Mapper;

@Mapper
public interface VisitorParkingCardEntityMapper extends CommonDomainModelMapper<VisitorParkingCard, VisitorParkingCardEntity> {
   // no-op
}