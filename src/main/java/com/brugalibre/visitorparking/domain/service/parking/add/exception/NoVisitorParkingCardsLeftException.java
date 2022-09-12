package com.brugalibre.visitorparking.domain.service.parking.add.exception;

import com.brugalibre.common.domain.exception.CommonDomainException;
import com.brugalibre.visitorparking.domain.model.resident.Resident;

public class NoVisitorParkingCardsLeftException extends CommonDomainException {

   public static final String NO_VISITOR_PARKING_CARDS_LEFT_ERROR_TEXT = "Resident {} has no visitor-parking cards left";

   public NoVisitorParkingCardsLeftException(Resident resident) {
      super(NO_VISITOR_PARKING_CARDS_LEFT_ERROR_TEXT, resident);
   }
}
