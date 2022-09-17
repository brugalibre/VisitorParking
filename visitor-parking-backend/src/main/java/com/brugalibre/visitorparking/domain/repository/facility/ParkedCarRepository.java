package com.brugalibre.visitorparking.domain.repository.facility;

import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.persistence.parking.ParkedCarDao;
import com.brugalibre.visitorparking.persistence.parking.ParkedCarEntity;
import com.brugalibre.visitorparking.persistence.parking.mapper.ParkedCarEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkedCarRepository extends CommonDomainRepositoryImpl<ParkedCar, ParkedCarEntity, ParkedCarDao> {

   public static final String NO_PARKED_CAR_FOUND_BY_ID_ERROR_TEXT = "No parked car found by plate number {}!";

   @Autowired
   public ParkedCarRepository(ParkedCarDao parkedCarDao) {
      super(parkedCarDao, new ParkedCarEntityMapperImpl());
   }

   /**
    * Gets a {@link ParkedCar} by its plate number
    *
    * @param carPlateNo the car plate number
    * @return a {@link ParkedCar} by its plate number
    */
   public ParkedCar getByCarPlateNo(String carPlateNo) {
      return getEntityAndMap(() -> domainDao.getByCarPlateNo(carPlateNo), NO_PARKED_CAR_FOUND_BY_ID_ERROR_TEXT, carPlateNo);
   }
}
