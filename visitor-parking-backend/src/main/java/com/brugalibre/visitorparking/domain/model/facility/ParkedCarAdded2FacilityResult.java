package com.brugalibre.visitorparking.domain.model.facility;

import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;

/**
 * The result when a parked car was added to a facility
 *
 * @param facility   the {@link Facility}
 * @param parkingLot the {@link ParkingLot}
 */
public record ParkedCarAdded2FacilityResult(Facility facility, ParkingLot parkingLot, ParkedCar parkedCar,
                                            boolean isVisitorParkingCardNotValid) {
   public ParkedCarAdded2FacilityResult(Facility facility, ParkingLot parkingLot, ParkedCar parkedCar) {
      this(facility, parkingLot, parkedCar, false);
   }

}
