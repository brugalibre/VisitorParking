package com.brugalibre.visitorparking.domain.service.parking.add.exception;

import com.brugalibre.common.domain.exception.CommonDomainException;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;

public class ParkedCarAlreadyAssignedToVisitorParkingCardException extends CommonDomainException {

   public static final String PARKED_CAR_ALREADY_ASSIGNED_TO_VISITOR_PARKING_CARD_ERROR_TEXT = "Parked car {} has already a visitor-parking card assigned";

   public ParkedCarAlreadyAssignedToVisitorParkingCardException(ParkedCar parkedCar) {
      super(PARKED_CAR_ALREADY_ASSIGNED_TO_VISITOR_PARKING_CARD_ERROR_TEXT, parkedCar);
   }
}
