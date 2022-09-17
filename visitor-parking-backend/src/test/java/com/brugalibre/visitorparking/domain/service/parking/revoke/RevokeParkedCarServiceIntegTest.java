package com.brugalibre.visitorparking.domain.service.parking.revoke;

import com.brugalibre.common.domain.exception.CommonDomainException;
import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.app.config.VisitorParkingManagementPersistenceConfig;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository;
import com.brugalibre.visitorparking.domain.service.facility.FacilityService;
import com.brugalibre.visitorparking.domain.service.parking.add.ParkCarService;
import com.brugalibre.visitorparking.domain.service.parking.revoke.exception.NoVisitorParkingCardAssignedException;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository.NO_PARKED_CAR_FOUND_BY_ID_ERROR_TEXT;
import static com.brugalibre.visitorparking.domain.service.parking.revoke.exception.NoVisitorParkingCardAssignedException.NO_VISITOR_PARKING_CARDS_ASSIGNED_ERROR_TEXT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = VisitorParkingManagementPersistenceConfig.class)
class RevokeParkedCarServiceIntegTest {
   public static final String SG_1 = "SG-1";
   public static final String SG_2 = "SG-2";

   @Autowired
   private FacilityService facilityService;

   @Autowired
   private RevokeParkedCarService revokeParkedCarService;

   @Autowired
   private ParkCarService parkCarService;

   @Autowired
   private ParkedCarRepository parkedCarRepository;

   @Autowired
   private ResidentService residentService;

   @Test
   void revokeParkedCar4PlateNoHappyCase() {
      // Given
      String carPlateNo = SG_1;
      Facility facility = facilityService.createFacility("Test1", 4);

      Resident resident = residentService.createResident(facility, User.of("username1"), 1);

      // When
      resident = parkCarService.assignParkedCar4PlateNo(resident.getId(), carPlateNo);
      Resident changedResident = revokeParkedCarService.revokeParkedCar4PlateNo(resident.getId(), carPlateNo);
      ParkingLot parkingLot = changedResident.facility().parkingLot();

      boolean isParkedCarAssignedByPlateNo = parkCarService.isParkedCarAssignedByPlateNo(facility.getId(), carPlateNo);

      // Then
      assertThat(isParkedCarAssignedByPlateNo, is(false));
      assertThat(parkingLot.id(), is(CoreMatchers.notNullValue()));// verify that we have reloaded the ParkingLot
   }

   @Test
   void revokeParkedCar4PlateNo_NoParkedCarAtAll() {
      // Given
      String carPlateNo = SG_1;
      Facility facility = facilityService.createFacility("Test2", 4);
      Resident resident = residentService.createResident(facility, User.of("username2"), 1);

      // When
      NoDomainModelFoundException thrown = assertThrows(NoDomainModelFoundException.class,
              () -> revokeParkedCarService.revokeParkedCar4PlateNo(resident.getId(), carPlateNo),
              "Car can't be revoked since there exists no parked car by the given plate number"
      );

      // Then
      String expectedMessage = CommonDomainException.getExceptionMessage(NO_PARKED_CAR_FOUND_BY_ID_ERROR_TEXT, new Object[]{carPlateNo});
      assertThat(thrown.getMessage(), is(expectedMessage));
   }

   @Test
   void revokeParkedCar4PlateNo_NoParkedCarAssignedToResident() {
      // Given
      Facility facility = facilityService.createFacility("Test3", 4);
      Resident resident = residentService.createResident(facility, User.of("username3"), 1);
      String carPlateNo = SG_2;
      parkedCarRepository.save(ParkedCar.builder()
              .carPlateNo(carPlateNo)
              .build());
      // When
      NoVisitorParkingCardAssignedException thrown = assertThrows(NoVisitorParkingCardAssignedException.class,
              () -> revokeParkedCarService.revokeParkedCar4PlateNo(resident.getId(), carPlateNo),
              "Car can't be revoked since the resident has no parked cars assigned"
      );
      ParkedCar actualParkedCar = parkedCarRepository.getByCarPlateNo(carPlateNo);

      // Then
      String expectedMessage = CommonDomainException.getExceptionMessage(NO_VISITOR_PARKING_CARDS_ASSIGNED_ERROR_TEXT,
              new Object[]{resident, actualParkedCar});
      assertThat(thrown.getMessage(), is(expectedMessage));
   }
}