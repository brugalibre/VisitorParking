package com.brugalibre.visitorparking.domain.model.resident;

import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Represents one old school physical parking card, which where placed inside a parked car in order to
 * mark it as legally parked. A {@link VisitorParkingCard} is only valid for a certain amount of time (not implemented yet)
 * and also only for a certain {@link ParkingLot}
 */
@Slf4j
public record VisitorParkingCard(String id, String parkingCardNr, String parkingLotId, String parkedCarPlateNo,
                                 boolean isAvailable, LocalDateTime assignedSince) {
   @Builder
   public VisitorParkingCard {
   }

   private VisitorParkingCard createNewVisitorParkingCard(boolean isAvailable, String carPlateNo, LocalDateTime assignedSince) {
      return new VisitorParkingCard(this.id, this.parkingCardNr, this.parkingLotId, carPlateNo, isAvailable, assignedSince);
   }

   /**
    * Flags this {@link VisitorParkingCard} as available
    *
    * @return a new instance of this {@link VisitorParkingCard}
    */
   public VisitorParkingCard flagAsAvailable() {
      return createNewVisitorParkingCard(true, null, null);
   }

   /**
    * Flags this {@link VisitorParkingCard} as unavailable
    *
    * @param carPlateNo the plate number of the {@link ParkedCar}, this {@link VisitorParkingCard} is assigned to
    * @return a new instance of this {@link VisitorParkingCard}
    */
   public VisitorParkingCard assignToParkedCar(String carPlateNo) {
      return createNewVisitorParkingCard(false, carPlateNo, LocalDateTime.now());
   }

   /**
    * Returns <code>true</code> if this {@link VisitorParkingCard} is assigned to the given {@link ParkedCar} or <code>false</code> if not
    *
    * @param parkedCar the {@link ParkedCar} to verify it it has this {@link VisitorParkingCard} assigned
    * @return <code>true</code> if this {@link VisitorParkingCard} is assigned to the given {@link ParkedCar} or <code>false</code> if not
    */
   public boolean isAssignedTo(ParkedCar parkedCar) {
      return parkedCar.carPlateNo().equals(parkedCarPlateNo);
   }
}
