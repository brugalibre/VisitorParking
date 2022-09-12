package com.brugalibre.visitorparking.domain.model.resident;

import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;

public record AssignedParkedCarResult(Resident resident, ParkedCar parkedCar, boolean hasNoVisitorParkingCardsLeft,
                                      boolean hasParkedCarAlreadyVisitorCardAssigned,
                                      boolean isVisitorParkingCardNotValid) {

   public AssignedParkedCarResult(Resident resident, ParkedCar parkedCar) {
      this(resident, parkedCar, false, false, false);
   }

   /**
    * Creates a new {@link AssignedParkedCarResult} when the given {@link ParkedCar} has already a {@link VisitorParkingCard} assigned
    *
    * @param resident  the {@link Resident}
    * @param parkedCar the {@link ParkedCar}
    * @return a new {@link AssignedParkedCarResult}
    */
   public static AssignedParkedCarResult hasNoVisitorParkingCardsLeft(Resident resident, ParkedCar parkedCar) {
      return new AssignedParkedCarResult(resident, parkedCar, true, false, false);
   }

   /**
    * Creates a new {@link AssignedParkedCarResult} when the given {@link ParkedCar} has already a {@link VisitorParkingCard} assigned
    *
    * @param resident  the {@link Resident}
    * @param parkedCar the {@link ParkedCar}
    * @return a new {@link AssignedParkedCarResult}
    */
   public static AssignedParkedCarResult parkedCarIsAlreadyAssigned(Resident resident, ParkedCar parkedCar) {
      return new AssignedParkedCarResult(resident, parkedCar, false, true, false);
   }

   /**
    * Creates a new {@link AssignedParkedCarResult} when the given {@link ParkedCar} has already a {@link VisitorParkingCard} assigned
    *
    * @param resident  the {@link Resident}
    * @param parkedCar the {@link ParkedCar}
    * @return a new {@link AssignedParkedCarResult}
    */
   public static AssignedParkedCarResult isVisitorParkingCardNotValid(Resident resident, ParkedCar parkedCar) {
      return new AssignedParkedCarResult(resident, parkedCar, false, false, true);
   }
}
