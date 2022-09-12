package com.brugalibre.visitorparking.domain.model.facility.parking;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.util.ListUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * The {@link ParkingLot} defines the parking lot on which {@link ParkedCar} are parked
 * Each parking lot belongs to one or more {@link Facility}
 *
 * @param id         the id of the {@link ParkingLot}
 * @param capacity   the capacity of this {@link ParkingLot}
 * @param parkedCars the {@link ParkedCar}s of this {@link ParkingLot}
 */
@Slf4j
public record ParkingLot(String id, int capacity, List<ParkedCar> parkedCars) implements DomainModel {


   @Builder
   public ParkingLot {
   }

   /**
    * Verifies if there is any car of this {@link Facility} which has a visitor parking card
    *
    * @param carPlateNo the no of the car plate
    * @return <code>true</code> if there is a parked car associated with the given car plate which has a visitor parking card
    */
   public boolean hasParkedCarAssignedByPlateNr(String carPlateNo) {
      return parkedCars.stream()
              .anyMatch(parkedCar -> parkedCar.visitorParkingCard() != null && parkedCar.carPlateNo().equals(carPlateNo));
   }

   /**
    * Adds the given {@link ParkedCar} to this {@link ParkingLot}
    *
    * @param parkedCar the parked car to add
    * @return a new instance of this {@link ParkingLot} with an increased amount of parked cars
    */
   public ParkedCarAdded2ParkingLotResult addParkedCar(ParkedCar parkedCar) {
      log.info("Add parked car {} to parking lot {}", parkedCar, this);
      if (isVisitorParkingCardNotValid(parkedCar)) {
         log.error("Visitor parking lot card{} is not suitable for Parking lot {}!", parkedCar.visitorParkingCard(), parkedCar);
         return new ParkedCarAdded2ParkingLotResult(this, parkedCar, true);
      }
      List<ParkedCar> newParkedCars = new ArrayList<>(parkedCars == null ? new ArrayList<>() : parkedCars);
      ListUtil.replaceExistingDomainModelIfPresent(parkedCar, newParkedCars);
      return createAndReturnParkedCarAddedResult(parkedCar, newParkedCars);
   }

   /**
    * Removes a {@link ParkedCar} for the given plate-no from this {@link ParkingLot}.
    * If the parked car is not actually parked on this {@link ParkingLot} nothing further happens
    *
    * @param carPlateNo car plate of the {@link ParkedCar} which has to be removed
    * @return a new instance of this {@link ParkingLot} with a decreased amount of parked cars
    */
   public ParkingLot removeParkedCarByPlateNo(String carPlateNo) {
      log.info("Add parked car {} to parking lot {}", carPlateNo, this);
      List<ParkedCar> newParkedCars = new ArrayList<>(parkedCars == null ? new ArrayList<>() : parkedCars);
      removeParkedCarByPlateNo(carPlateNo, newParkedCars);
      return new ParkingLot(this.id, this.capacity, newParkedCars);
   }

   private static void removeParkedCarByPlateNo(String carPlateNo, List<ParkedCar> newParkedCars) {
      newParkedCars.stream()
              .filter(parkedCar1 -> carPlateNo.equals(parkedCar1.carPlateNo()))
              .findFirst()
              .ifPresent(newParkedCars::remove);
   }

   private ParkedCarAdded2ParkingLotResult createAndReturnParkedCarAddedResult(ParkedCar parkedCar, List<ParkedCar> newParkedCars) {
      ParkingLot parkingLot = new ParkingLot(this.id, this.capacity, newParkedCars);
      return new ParkedCarAdded2ParkingLotResult(parkingLot, parkedCar);
   }

   private boolean isVisitorParkingCardNotValid(ParkedCar parkedCar) {
      return isNull(parkedCar.visitorParkingCard()) || !this.id.equals(parkedCar.visitorParkingCard().parkingLotId());
   }

   @Override
   public String getId() {
      return this.id;
   }

}
