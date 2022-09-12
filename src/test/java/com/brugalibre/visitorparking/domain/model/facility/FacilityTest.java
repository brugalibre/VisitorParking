package com.brugalibre.visitorparking.domain.model.facility;

import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.domain.model.security.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class FacilityTest {

   @Test
   void hasParkedCarAssignedByPlateNr() {
   }

   @Test
   void addParkedCarHappyCase() {
      // Given
      String facilityId = "999";
      String parkingLotId = "999";
      String carPlateNo = "123";
      Facility facility = Facility.builder()
              .id(facilityId)
              .parkingLot(ParkingLot.builder()
                      .capacity(3)
                      .id(parkingLotId)
                      .parkedCars(new ArrayList<>())
                      .build())
              .build();
      ParkedCar parkedCar = ParkedCar.builder()
              .visitorParkingCard(VisitorParkingCard.builder()
                      .parkingCardNr("321")
                      .parkingLotId(parkingLotId)
                      .build())
              .carPlateNo(carPlateNo)
              .build();

      // When
      ParkedCarAdded2FacilityResult parkedCarAdded2FacilityResult = facility.addParkedCar(parkedCar);
      Facility actualFacility = parkedCarAdded2FacilityResult.facility();
      ParkingLot actualParkingLot = parkedCarAdded2FacilityResult.parkingLot();
      boolean hasParkedCarAssignedByPlateNr = actualFacility.hasParkedCarAssignedByPlateNr(carPlateNo);

      // Then
      assertThat(hasParkedCarAssignedByPlateNr, is(true));
      assertThat(actualFacility.getId(), is(facilityId));
      assertThat(actualParkingLot.parkedCars().size(), is(1));
   }

   @Test
   void removeParkedCarHappyCase() {

      // Given
      String facilityId = "999";
      String parkingLotId = "999";
      String carPlateNo = "123";
      ParkedCar parkedCar = ParkedCar.builder()
              .visitorParkingCard(VisitorParkingCard.builder()
                      .parkingCardNr("321")
                      .parkingLotId(parkingLotId)
                      .build())
              .carPlateNo(carPlateNo)
              .build();
      Facility facility = Facility.builder()
              .id(facilityId)
              .parkingLot(ParkingLot.builder()
                      .capacity(3)
                      .id(parkingLotId)
                      .parkedCars(List.of(parkedCar))
                      .build())
              .build();

      // When
      boolean hasParkedCarAssignedBeforeRemove = facility.hasParkedCarAssignedByPlateNr(carPlateNo);
      Facility actualFacility = facility.removeParkedCar(parkedCar.carPlateNo());
      boolean hasParkedCarAssignedAfterRemove = actualFacility.hasParkedCarAssignedByPlateNr(carPlateNo);

      // Then
      assertThat(hasParkedCarAssignedBeforeRemove, is(true));
      assertThat(hasParkedCarAssignedAfterRemove, is(false));
   }

   @Test
   void addParkedCarWrongParkingCard() {

      // Given
      String facilityId = "999";
      String parkingLotId1 = "999";
      String parkingLotId2 = "123";
      String carPlateNo = "123";
      Facility facility = Facility.builder()
              .id(facilityId)
              .parkingLot(ParkingLot.builder()
                      .capacity(3)
                      .id(parkingLotId1)
                      .parkedCars(new ArrayList<>())
                      .build())
              .build();
      VisitorParkingCard visitorParkingCard = VisitorParkingCard.builder()
              .parkingCardNr("321")
              .parkingLotId(parkingLotId2)
              .build();
      ParkedCar parkedCar = ParkedCar.builder()
              .visitorParkingCard(visitorParkingCard)
              .carPlateNo(carPlateNo)
              .build();

      // When
      ParkedCarAdded2FacilityResult parkedCarAdded2FacilityResult = facility.addParkedCar(parkedCar);

      // Then
      assertThat(parkedCarAdded2FacilityResult.isVisitorParkingCardNotValid(), is(true));
   }

   @Test
   void addParkedCarNoParkingCard() {

      // Given
      String facilityId = "999";
      String parkingLotId1 = "999";
      String carPlateNo = "123";
      Facility facility = Facility.builder()
              .id(facilityId)
              .parkingLot(ParkingLot.builder()
                      .capacity(3)
                      .id(parkingLotId1)
                      .parkedCars(new ArrayList<>())
                      .build())
              .build();
      ParkedCar parkedCar = ParkedCar.builder()
              .visitorParkingCard(null)
              .carPlateNo(carPlateNo)
              .build();

      // When
      ParkedCarAdded2FacilityResult parkedCarAdded2FacilityResult = facility.addParkedCar(parkedCar);

      // Then
      assertThat(parkedCarAdded2FacilityResult.isVisitorParkingCardNotValid(), is(true));
   }

   @Test
   void addResident() {
      // Given
      String facilityId = "999";
      Facility facility = Facility.builder()
              .id(facilityId)
              .residents(new ArrayList<>())
              .build();
      Resident resident1 = Resident.builder()
              .id("1")
              .user(new User("user1", "pw", List.of()))
              .build();
      Resident resident2 = Resident.builder()
              .id("2")
              .user(new User("user2", "pw", List.of()))
              .build();

      // When
      facility = facility.addResident(resident1)
              .addResident(resident1)
              .addResident(resident2);

      // Then
      assertThat(facility.residents().size(), is(2));
   }
}