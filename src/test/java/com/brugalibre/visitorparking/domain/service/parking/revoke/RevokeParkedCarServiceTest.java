package com.brugalibre.visitorparking.domain.service.parking.revoke;

import com.brugalibre.common.domain.exception.CommonDomainException;
import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.security.user.User;
import com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository;
import com.brugalibre.visitorparking.domain.service.facility.FacilityService;
import com.brugalibre.visitorparking.domain.service.parking.add.ParkCarService;
import com.brugalibre.visitorparking.domain.service.parking.revoke.exception.NoVisitorParkingCardAssignedException;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import com.brugalibre.visitorparking.persistence.config.VisitorParkingManagementPersistenceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository.NO_PARKED_CAR_FOUND_BY_ID_ERROR_TEXT;
import static com.brugalibre.visitorparking.domain.service.parking.revoke.exception.NoVisitorParkingCardAssignedException.NO_VISITOR_PARKING_CARDS_ASSIGNED_ERROR_TEXT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = VisitorParkingManagementPersistenceConfig.class)
class RevokeParkedCarServiceTest {
   public static final String SG_1 = "SG-1";
   public static final String SG_2 = "SG-2";
   public static final String SG_3 = "SG-3";

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
   void revokeParkedCar4PlateNoHappycase() {
      // Given
      Facility facility = facilityService.createFacility("Test1", 4);
      Resident resident = residentService.createResident(facility, new User("user1", "pw", List.of()), 1);

      // When
      parkCarService.assignParkedCar4PlateNo(resident.getId(), SG_1);
      revokeParkedCarService.revokeParkedCar4PlateNo(resident.getId(), SG_1);

      boolean hasParkedCarAssignedByPlateNo = parkCarService.hasParkedCarAssignedByPlateNo(resident.getId(), SG_1);

      // Then
      assertThat(hasParkedCarAssignedByPlateNo, is(false));
   }

   @Test
   void revokeParkedCar4PlateNo_NoParkedCarAtAll() {
      // Given
      Facility facility = facilityService.createFacility("Test2", 4);
      Resident resident = residentService.createResident(facility, new User("user2", "pw", List.of()), 1);

      // When
      NoDomainModelFoundException thrown = assertThrows(NoDomainModelFoundException.class,
              () -> revokeParkedCarService.revokeParkedCar4PlateNo(resident.getId(), SG_1),
              "Car can't be revoked since there exists no parked car by the given plate number"
      );

      // Then
      String expectedMessage = CommonDomainException.getExceptionMessage(NO_PARKED_CAR_FOUND_BY_ID_ERROR_TEXT, new Object[]{SG_2});
      assertThat(thrown.getMessage(), is(expectedMessage));
   }

   @Test
   void revokeParkedCar4PlateNo_NoParkedCarAssignedToResident() {
      // Given
      Facility facility = facilityService.createFacility("Test3", 4);
      Resident resident = residentService.createResident(facility, new User("user3", "pw", List.of()), 1);
      parkedCarRepository.save(ParkedCar.builder()
              .carPlateNo(SG_3)
              .build());
      // When
      NoVisitorParkingCardAssignedException thrown = assertThrows(NoVisitorParkingCardAssignedException.class,
              () -> revokeParkedCarService.revokeParkedCar4PlateNo(resident.getId(), SG_3),
              "Car can't be revoked since the resident has no parkec cars assigned"
      );
      ParkedCar actualParkedCar = parkedCarRepository.getByCarPlateNo(SG_3);

      // Then
      String expectedMessage = CommonDomainException.getExceptionMessage(NO_VISITOR_PARKING_CARDS_ASSIGNED_ERROR_TEXT,
              new Object[]{resident, actualParkedCar});
      assertThat(thrown.getMessage(), is(expectedMessage));
   }
}