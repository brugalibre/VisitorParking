package com.brugalibre.visitorparking.domain.model.resident;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.ParkedCarAdded2FacilityResult;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResidentTest {

   @Test
   void assignParkedCar_NoCardsLeft() {

      // Given
      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo("13254124565456")
              .build();
      Resident resident = Resident.builder()
              .id("123")
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingLotId("465")
                      .parkingCardNr(UUID.randomUUID().toString())
                      .isAvailable(false)
                      .build()))
              .build();

      // When
      AssignedParkedCarResult assignedParkedCarResult = resident.addParkedCarAndAssignVisitorParkingCard(parkedCar);

      // Then
      assertThat(assignedParkedCarResult.isVisitorParkingCardNotValid(), is(false));
      assertThat(assignedParkedCarResult.areNoVisitorParkingCardsLeft(), is(true));
   }

   @Test
   void assignParkedCar() {

      // Given
      String carPlateNo = "13254124565456";
      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo(carPlateNo)
              .build();
      Facility facility = mockFacility(false, parkedCar);
      String id = "123";
      Resident resident = Resident.builder()
              .id(id)
              .facility(facility)
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingLotId("465")
                      .isAvailable(true)
                      .parkingCardNr(UUID.randomUUID().toString())
                      .build()))
              .build();

      // When
      AssignedParkedCarResult assignedParkedCarResult = resident.addParkedCarAndAssignVisitorParkingCard(parkedCar);
      ParkedCar actualParkedCar = assignedParkedCarResult.parkedCar();
      boolean actualIsParkedCarAssigned = assignedParkedCarResult.resident().isParkedCarAssigned(carPlateNo);

      // Then
      VisitorParkingCard actualVisitorParkingCard = actualParkedCar.visitorParkingCard();
      assertThat(actualVisitorParkingCard.isAvailable(), is(false));
      assertThat(actualVisitorParkingCard.parkedCarPlateNo(), is(carPlateNo));
      assertThat(actualVisitorParkingCard.assignedSince().toLocalDate(), is(LocalDate.now()));
      assertThat(actualIsParkedCarAssigned, is(true));
      assertThat(resident.getId(), is(id));
   }

   @Test
   void assignSameParkedCarTwice() {

      // Given
      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo("13254124565456")
              .build();
      Facility facility = mockFacility(false, parkedCar);
      String id = "123";
      Resident resident = Resident.builder()
              .id(id)
              .facility(facility)
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingLotId("465")
                      .isAvailable(true)
                      .parkingCardNr(UUID.randomUUID().toString())
                      .build()))
              .build();

      // When
      AssignedParkedCarResult assignedParkedCarResultFirstTime = resident.addParkedCarAndAssignVisitorParkingCard(parkedCar);
      AssignedParkedCarResult assignedParkedCarResultSecondTime = assignedParkedCarResultFirstTime.resident()
              .addParkedCarAndAssignVisitorParkingCard(assignedParkedCarResultFirstTime.parkedCar());

      // Then
      assertThat(assignedParkedCarResultSecondTime.isParkedCarAlreadyVisitorCardAssigned(), is(true));
      assertThat(assignedParkedCarResultFirstTime.isVisitorParkingCardNotValid(), is(false));
   }

   @Test
   void assignInvalidParkingCard2ParkedCar() {

      // Given
      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo("13254124565456")
              .build();
      Facility facility = mockFacility(true, parkedCar);
      String id = "123";
      Resident resident = Resident.builder()
              .id(id)
              .facility(facility)
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingLotId("465")
                      .isAvailable(true)
                      .parkingCardNr(UUID.randomUUID().toString())
                      .build()))
              .build();

      // When
      AssignedParkedCarResult assignedParkedCarResultFirstTime = resident.addParkedCarAndAssignVisitorParkingCard(parkedCar);

      // Then
      assertThat(assignedParkedCarResultFirstTime.isVisitorParkingCardNotValid(), is(true));
   }

   @Test
   void revokeParkedCar() {
      // Given
      String carPlateNo = "13254124565456";

      VisitorParkingCard visitorParkingCard = VisitorParkingCard.builder()
              .parkingLotId("465")
              .isAvailable(false)
              .parkedCarPlateNo(carPlateNo)
              .parkingCardNr(UUID.randomUUID().toString())
              .build();

      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo(carPlateNo)
              .build()
              .assignVisitorParkingCard(visitorParkingCard);
      Facility facility = mockFacility(false, parkedCar);
      Resident resident = Resident.builder()
              .id("123")
              .facility(facility)
              .visitorParkingCards(List.of(parkedCar.visitorParkingCard()))
              .build();

      // When
      RemoveParkedCarResult removeParkedCarResult = resident.removeParkedCarAndRevokeVisitorParkingCard(parkedCar);
      ParkedCar actualParkedCar = removeParkedCarResult.parkedCar();
      boolean actualIsParkedCarAssigned = removeParkedCarResult.resident().isParkedCarAssigned(carPlateNo);

      // Then
      VisitorParkingCard actualVisitorParkingCard = actualParkedCar.visitorParkingCard();
      assertThat(actualVisitorParkingCard.isAvailable(), is(true));
      assertThat(actualVisitorParkingCard.assignedSince(), is(nullValue()));
      assertThat(actualVisitorParkingCard.parkedCarPlateNo(), is(nullValue()));
      assertThat(actualIsParkedCarAssigned, is(false));
   }

   @Test
   void revokeParkedCarNoVisitorParkingCardAssigned() {
      // Given

      VisitorParkingCard visitorParkingCard = VisitorParkingCard.builder()
              .parkingLotId("465")
              .isAvailable(false)
              .parkingCardNr(UUID.randomUUID().toString())
              .build();

      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo("13254124565456")
              .build();
      Facility facility = mockFacility(false, parkedCar);
      Resident resident = Resident.builder()
              .id("123")
              .facility(facility)
              .visitorParkingCards(List.of(visitorParkingCard))
              .build();

      // When
      RemoveParkedCarResult removeParkedCarResult = resident.removeParkedCarAndRevokeVisitorParkingCard(parkedCar);

      // Then
      assertThat(removeParkedCarResult.isNoVisitorParkingCardAssigned(), is(true));
   }

   private static Facility mockFacility(boolean isVisitorParkingCardNotValid, ParkedCar parkedCar) {
      Facility facility = mock(Facility.class);
      when(facility.addParkedCar(any())).thenReturn(new ParkedCarAdded2FacilityResult(facility, null, parkedCar, isVisitorParkingCardNotValid));
      when(facility.removeParkedCar(any())).thenReturn(facility);
      return facility;
   }
}