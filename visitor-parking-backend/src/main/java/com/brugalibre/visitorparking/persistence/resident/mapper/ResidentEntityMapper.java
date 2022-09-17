package com.brugalibre.visitorparking.persistence.resident.mapper;

import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.domain.user.mapper.UserEntityMapper;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.persistence.facility.mapper.FacilityEntityOnlyMapper;
import com.brugalibre.visitorparking.persistence.resident.ResidentEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {FacilityEntityOnlyMapper.class, VisitorParkingCardEntityMapper.class, UserEntityMapper.class})
public interface ResidentEntityMapper extends CommonDomainModelMapper<Resident, ResidentEntity> {
   // no-op
}