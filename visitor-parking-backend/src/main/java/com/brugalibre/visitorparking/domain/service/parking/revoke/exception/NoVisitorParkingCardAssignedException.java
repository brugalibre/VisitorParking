package com.brugalibre.visitorparking.domain.service.parking.revoke.exception;

import com.brugalibre.common.domain.exception.CommonDomainException;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.resident.Resident;

public class NoVisitorParkingCardAssignedException extends CommonDomainException {

   public static final String NO_VISITOR_PARKING_CARDS_ASSIGNED_ERROR_TEXT = "Resident {} has no visitor-parking assigned to parked car {}";

   public NoVisitorParkingCardAssignedException(Resident resident, ParkedCar parkedCar) {
      super(NO_VISITOR_PARKING_CARDS_ASSIGNED_ERROR_TEXT, resident, parkedCar);
   }
}
