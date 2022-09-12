package com.brugalibre.visitorparking.domain.model.facility;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCarAdded2ParkingLotResult;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.util.ListUtil;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link Facility} defines the building in which a {@link Resident} lives and as well as an optional {@link ParkingLot}
 * for parking the cars. The parking lot may be shared between multiple facilities
 */
public record Facility(String id, String name, List<Resident> residents, ParkingLot parkingLot) implements DomainModel {
   @Builder
   public Facility {
   }

   @Override
   public String getId() {
      return this.id;
   }

   /**
    * Verifies if there is any car of this {@link Facility} which has a visitor parking card
    *
    * @param carPlateNo the no of the car plate
    * @return <code>true</code> if there is a parked car associated with the given car plate which has a visitor parking card
    */
   public boolean hasParkedCarAssignedByPlateNr(String carPlateNo) {
      return parkingLot.hasParkedCarAssignedByPlateNr(carPlateNo);
   }

   /**
    * Parks the given {@link ParkedCar} on the {@link ParkingLot} of this {@link Facility}
    *
    * @param parkedCar the parked car which is now on the {@link ParkingLot}
    * @return a {@link ParkedCarAdded2FacilityResult} which contains a new instance of a {@link Facility} as well as the {@link ParkingLot}
    */
   public ParkedCarAdded2FacilityResult addParkedCar(ParkedCar parkedCar) {
      ParkedCarAdded2ParkingLotResult parkedCarAdded2ParkingLotResult = parkingLot.addParkedCar(parkedCar);
      return createAndReturnParkedCarAdded2FacilityResult(parkedCarAdded2ParkingLotResult);
   }

   /**
    * Removes the given {@link ParkedCar} from the {@link ParkingLot} of this {@link Facility}
    *
    * @param carPlateNo car plate of the {@link ParkedCar} which has to be removed
    * @return a new instance of a {@link Facility}
    */
   public Facility removeParkedCar(String carPlateNo) {
      return new Facility(this.id, this.name, this.residents, parkingLot.removeParkedCarByPlateNo(carPlateNo));
   }

   /**
    * Adds the given {@link Resident} to this {@link Facility}. If the resident already belongs to this {@link Facility}
    * nothing happens
    *
    * @param resident the {@link Resident} to add
    * @return a new {@link Facility} instance with the additional {@link Resident}
    */
   public Facility addResident(Resident resident) {
      List<Resident> newResidents = new ArrayList<>(residents == null ? new ArrayList<>() : residents);
      ListUtil.replaceExistingDomainModelIfPresent(resident, newResidents);
      return new Facility(this.id, this.name, newResidents, parkingLot);
   }

   private ParkedCarAdded2FacilityResult createAndReturnParkedCarAdded2FacilityResult(ParkedCarAdded2ParkingLotResult parkedCarAdded2ParkingLotResult) {
      Facility facility = new Facility(this.id, this.name, this.residents, parkedCarAdded2ParkingLotResult.parkingLot());
      return new ParkedCarAdded2FacilityResult(facility, parkedCarAdded2ParkingLotResult.parkingLot(),
              parkedCarAdded2ParkingLotResult.isVisitorParkingCardNotValid());
   }
}
