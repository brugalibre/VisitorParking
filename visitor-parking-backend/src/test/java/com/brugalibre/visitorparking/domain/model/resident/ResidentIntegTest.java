package com.brugalibre.visitorparking.domain.model.resident;

import com.brugalibre.visitorparking.app.config.VisitorParkingManagementPersistenceConfig;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.repository.facility.FacilityRepository;
import com.brugalibre.visitorparking.domain.repository.resident.ResidentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = VisitorParkingManagementPersistenceConfig.class)
class ResidentIntegTest {

   private static final String SG_1 = "SG-1";
   private static final String SG_2 = "SG-2";
   private static final String SG_3 = "SG-3";
   public static final String PARKING_CARD_NR_1 = "1234";
   public static final String PARKING_CARD_NR_2 = "12345";
   public static final String PARKING_CARD_NR_3 = "123456";

   @Autowired
   private ResidentRepository residentRepository;

   @Autowired
   private FacilityRepository facilityRepository;

   @Test
   void testCreateResidentAndAddParkedCar() {
      // Given
      Facility facility = buildDefaultFacility();

      // When - persist facility & resident. Resident must be saved after the facility, we need the parking lot id
      facility = facilityRepository.save(facility);
      Resident resident = residentRepository.save(buildDefaultResidentWithOneVisitorParkingCard("123", facility, PARKING_CARD_NR_1));

      AssignedParkedCarResult assignedParkedCarResult = resident.addParkedCarAndAssignVisitorParkingCard(ParkedCar.builder()
              .carPlateNo(SG_1)
              .build());

      // Then
      ParkedCar parkedCar = assignedParkedCarResult.parkedCar();
      resident = assignedParkedCarResult.resident();
      facility = assignedParkedCarResult.resident().facility();
      VisitorParkingCard visitorParkingCard = parkedCar.visitorParkingCard();

      boolean isParkedCarAssigned = resident.isParkedCarAssigned(SG_1);
      boolean isParkedCarAssignedByPlateNr = facility.isParkedCarAssignedByPlateNr(SG_1);

      assertThat(isParkedCarAssigned, is(true));
      assertThat(isParkedCarAssignedByPlateNr, is(true));
      assertThat(visitorParkingCard.parkedCarPlateNo(), is(SG_1));
      assertThat(visitorParkingCard.parkingCardNr(), is(PARKING_CARD_NR_1));
      assertThat(visitorParkingCard.isAvailable(), is(false));
      assertThat(visitorParkingCard.assignedSince().toLocalDate(), is(LocalDate.now()));
   }

   @Test
   void testCreateResidentAddParkedCarAndRevokeAgain() {
      // Given
      Facility facility = buildDefaultFacility();

      // When - persist facility & resident. Resident must be saved after the facility, we need the parking lot id
      facility = facilityRepository.save(facility);
      Resident resident = residentRepository.save(buildDefaultResidentWithOneVisitorParkingCard("1234", facility, PARKING_CARD_NR_2));

      // add the car
      AssignedParkedCarResult assignedParkedCarResult = resident.addParkedCarAndAssignVisitorParkingCard(ParkedCar.builder()
              .carPlateNo(SG_2)
              .build());
      ParkedCar assignedParkedCar = assignedParkedCarResult.parkedCar();
      resident = assignedParkedCarResult.resident();

      // remove again
      RemoveParkedCarResult removeParkedCarResult = resident.removeParkedCarAndRevokeVisitorParkingCard(assignedParkedCar);
      assignedParkedCar = removeParkedCarResult.parkedCar();
      resident = residentRepository.save(removeParkedCarResult.resident());
      facility = facilityRepository.save(resident.facility());

      // Then
      VisitorParkingCard visitorParkingCard = assignedParkedCar.visitorParkingCard();

      boolean isParkedCarAssigned = resident.isParkedCarAssigned(SG_2);
      boolean isParkedCarAssignedByPlateNr = facility.isParkedCarAssignedByPlateNr(SG_2);

      assertThat(isParkedCarAssigned, is(false));
      assertThat(isParkedCarAssignedByPlateNr, is(false));
      assertThat(visitorParkingCard.parkingCardNr(), is(PARKING_CARD_NR_2));
      assertThat(visitorParkingCard.isAvailable(), is(true));
      assertThat(visitorParkingCard.parkedCarPlateNo(), is(nullValue()));
      assertThat(visitorParkingCard.assignedSince(), is(nullValue()));
   }

   @Test
   void testAddTheSameParkedCarTwice() {
      // Given
      Facility facility = buildDefaultFacility();

      // When - persist facility & resident. Resident must be saved after the facility, we need the parking lot id
      facility = facilityRepository.save(facility);
      Resident resident = residentRepository.save(buildDefaultResidentWithOneVisitorParkingCard("12345", facility, PARKING_CARD_NR_3));

      AssignedParkedCarResult assignedParkedCarResult = resident.addParkedCarAndAssignVisitorParkingCard(ParkedCar.builder()
              .carPlateNo(SG_3)
              .build());
      assignedParkedCarResult = assignedParkedCarResult.resident()
              .addParkedCarAndAssignVisitorParkingCard(ParkedCar.builder()
                      .carPlateNo(SG_3)
                      .build());

      resident = assignedParkedCarResult.resident();

      boolean isParkedCarAssigned = resident.isParkedCarAssigned(SG_3);

      // Then
      assertThat(isParkedCarAssigned, is(true));
      assertThat(assignedParkedCarResult.isParkedCarAlreadyVisitorCardAssigned(), is(true));
   }

   private static Facility buildDefaultFacility() {
      return Facility.builder()
              .parkingLot(ParkingLot.builder()
                      .capacity(2)
                      .parkedCars(new ArrayList<>())
                      .build())
              .build();
   }

   private static Resident buildDefaultResidentWithOneVisitorParkingCard(String userId, Facility facility, String parkingCardNr) {
      return Resident.builder()
              .facility(facility)
              .userId(userId)
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingCardNr(parkingCardNr)
                      .isAvailable(true)
                      .parkingLotId(facility.parkingLot().getId())
                      .build()))
              .build();
   }
}