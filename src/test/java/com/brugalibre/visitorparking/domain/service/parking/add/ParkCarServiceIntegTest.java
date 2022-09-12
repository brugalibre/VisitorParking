package com.brugalibre.visitorparking.domain.service.parking.add;

import com.brugalibre.common.domain.exception.CommonDomainException;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.security.user.User;
import com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository;
import com.brugalibre.visitorparking.domain.service.facility.FacilityService;
import com.brugalibre.visitorparking.domain.service.parking.add.ParkCarService;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.NoVisitorParkingCardsLeftException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.ParkedCarAlreadyAssignedToVisitorParkingCardException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.VisitorParkingCardNotValidException;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import com.brugalibre.visitorparking.persistence.config.VisitorParkingManagementPersistenceConfig;
import com.brugalibre.visitorparking.persistence.resident.VisitorParkingCardDao;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.brugalibre.visitorparking.domain.service.parking.add.exception.NoVisitorParkingCardsLeftException.NO_VISITOR_PARKING_CARDS_LEFT_ERROR_TEXT;
import static com.brugalibre.visitorparking.domain.service.parking.add.exception.ParkedCarAlreadyAssignedToVisitorParkingCardException.PARKED_CAR_ALREADY_ASSIGNED_TO_VISITOR_PARKING_CARD_ERROR_TEXT;
import static com.brugalibre.visitorparking.domain.service.parking.add.exception.VisitorParkingCardNotValidException.INVALID_PARKING_CARD_ERROR_TEXT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = VisitorParkingManagementPersistenceConfig.class)
class ParkCarServiceIntegTest {

   public static final String SG_1 = "SG-1";
   public static final String SG_2 = "SG-2";

   @Autowired
   private FacilityService facilityService;

   @Autowired
   private ParkCarService parkCarService;

   @Autowired
   private ParkedCarRepository parkedCarRepository;

   @Autowired
   private ResidentService residentService;

   @Autowired
   private VisitorParkingCardDao visitorParkingCardDao;

   @Test
   void assignParkedCar_NoCardsLeft() {

      // Given
      Facility facility = facilityService.createFacility("Test", 4);
      Resident resident = residentService.createResident(facility, new User("user1", "pw", List.of()), 0);

      // When
      NoVisitorParkingCardsLeftException thrown = assertThrows(NoVisitorParkingCardsLeftException.class,
              () -> parkCarService.assignParkedCar4PlateNo(resident.getId(), SG_1),
              "Car must not be parked since there are no visitor-parking cards left"
      );

      // Then
      String expectedMessage = CommonDomainException.getExceptionMessage(NO_VISITOR_PARKING_CARDS_LEFT_ERROR_TEXT, new Object[]{resident});
      assertThat(thrown.getMessage(), is(expectedMessage));
   }

   @Test
   void assignParkedCarTwice() {

      // Given
      Facility facility = facilityService.createFacility("Test2", 4);
      Resident resident = residentService.createResident(facility, new User("user2", "pw", List.of()), 1);

      // When
      parkCarService.assignParkedCar4PlateNo(resident.getId(), SG_2);
      ParkedCarAlreadyAssignedToVisitorParkingCardException thrown = assertThrows(ParkedCarAlreadyAssignedToVisitorParkingCardException.class,
              () -> parkCarService.assignParkedCar4PlateNo(resident.getId(), SG_2),
              "Car must not be parked since the same car is already parked"
      );
      ParkedCar actualParkedCar = parkedCarRepository.getByCarPlateNo(SG_2);

      // Then
      String expectedMessage = CommonDomainException.getExceptionMessage(PARKED_CAR_ALREADY_ASSIGNED_TO_VISITOR_PARKING_CARD_ERROR_TEXT,
              new Object[]{actualParkedCar});
      assertThat(thrown.getMessage(), is(expectedMessage));
   }

   @Test
   void assignParkedCarWithoutValidVisitorParkingCard() {

      // Given
      Facility facility = facilityService.createFacility("Test3", 4);
      Resident resident = residentService.createResident(facility, new User("user3", "pw", List.of()), 1);
      ParkedCar car2Park = ParkedCar.builder()
              .carPlateNo(SG_2)
              .build();

      // No change the parking-lot id of the visitorParkingCardEntity and make it invalid
      visitorParkingCardDao.findById(resident.visitorParkingCards().get(0).id())
              .ifPresent(visitorParkingCardEntity -> {
                 visitorParkingCardEntity.setParkingLotId("123IDontExist");
                 visitorParkingCardDao.save(visitorParkingCardEntity);
              });

      // When
      VisitorParkingCardNotValidException thrown = assertThrows(VisitorParkingCardNotValidException.class,
              () -> parkCarService.assignParkedCar4PlateNo(resident.getId(), SG_2),
              "Car must not be parked since the visitor parking card is not valid"
      );

      // Then
      String expectedMessage = CommonDomainException.getExceptionMessage(INVALID_PARKING_CARD_ERROR_TEXT, new Object[]{car2Park});
      assertThat(thrown.getMessage(), is(expectedMessage));
   }
}