package com.brugalibre.visitorparking.domain.repository.facility;

import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.persistence.facility.FacilityDao;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import com.brugalibre.visitorparking.persistence.facility.mapper.FacilityEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacilityRepository extends CommonDomainRepositoryImpl<Facility, FacilityEntity, FacilityDao> {
   @Autowired
   public FacilityRepository(FacilityDao facilityDao) {
      super(facilityDao, new FacilityEntityMapperImpl());
   }
}
