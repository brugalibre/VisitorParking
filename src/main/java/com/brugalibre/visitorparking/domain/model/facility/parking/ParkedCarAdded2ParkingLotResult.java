package com.brugalibre.visitorparking.domain.model.facility.parking;

/**
 * The result when a parked car was added to a parking lot
 *
 * @param parkingLot the {@link ParkingLot}
 * @param parkedCar  the {@link ParkedCar}
 */
public record ParkedCarAdded2ParkingLotResult(ParkingLot parkingLot, ParkedCar parkedCar, boolean isVisitorParkingCardNotValid) {
   public ParkedCarAdded2ParkingLotResult(ParkingLot parkingLot, ParkedCar parkedCar) {
      this(parkingLot, parkedCar, false);
   }
}
