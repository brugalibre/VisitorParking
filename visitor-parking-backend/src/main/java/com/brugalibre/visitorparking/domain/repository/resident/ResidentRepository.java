package com.brugalibre.visitorparking.domain.repository.resident;

import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.domain.user.model.User;
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

   /**
    * Returns the {@link Resident} associated to the given user id
    *
    * @param userId the id uf a {@link User} to find the resident
    * @return the {@link Resident} associated to the given id
    * @throws NoDomainModelFoundException if there is no {@link Resident} associated with the given id
    */
   public Resident getByUserId(String userId) {
      ResidentEntity residentEntity = domainDao.getByUserId(userId);
      return domainModelMapper.map2DomainModel(residentEntity);
   }
}
