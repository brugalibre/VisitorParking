package com.brugalibre.visitorparking.domain.service.parking.add.exception;

import com.brugalibre.common.domain.exception.CommonDomainException;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;

public class VisitorParkingCardNotValidException extends CommonDomainException {
   public static final String INVALID_PARKING_CARD_ERROR_TEXT = "The parked car {} can't be parked, due to it's invalid parking card";

   public VisitorParkingCardNotValidException(ParkedCar parkedCar) {
      super(INVALID_PARKING_CARD_ERROR_TEXT, parkedCar);
   }

}
