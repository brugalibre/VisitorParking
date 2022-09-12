package com.brugalibre.visitorparking.domain.repository.resident;

import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.persistence.resident.ResidentDao;
import com.brugalibre.visitorparking.persistence.resident.ResidentEntity;
import com.brugalibre.visitorparking.persistence.resident.mapper.ResidentEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResidentRepository extends CommonDomainRepositoryImpl<Resident, ResidentEntity, ResidentDao> {

   @Autowired
   public ResidentRepository(ResidentDao residentDao) {
      super(residentDao, new ResidentEntityMapperImpl());
   }
}
