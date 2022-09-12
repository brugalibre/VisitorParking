package com.brugalibre.visitorparking.domain.model.resident;

import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;

public record RemoveParkedCarResult(Resident resident, ParkedCar parkedCar, boolean hasNoVisitorParkingCardAssigned) {

   public RemoveParkedCarResult(Resident resident, ParkedCar parkedCar) {
      this(resident, parkedCar, false);
   }

}
