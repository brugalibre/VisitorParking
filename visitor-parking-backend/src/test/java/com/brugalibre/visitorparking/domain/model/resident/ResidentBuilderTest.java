package com.brugalibre.visitorparking.domain.model.resident;

import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.resident.builder.ResidentBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ResidentBuilderTest {

   @Test
   void buildResident() {
      // Given
      String parkingCardNr = "parkingCardNr";
      ResidentBuilder residentBuilder = new ResidentBuilder(() -> parkingCardNr);
      String parkingLotId = "324";
      Facility facility = Facility.builder()
              .parkingLot(ParkingLot.builder()
                      .id(parkingLotId)
                      .build())
              .build();
      int amountOfVisitorParkingCards = 4;

      // When
      Resident resident = residentBuilder.buildResident(facility, User.of("username"), amountOfVisitorParkingCards);

      // Then
      assertThat(resident.facility(), is(facility));
      assertThat(resident.visitorParkingCards().size(), is(amountOfVisitorParkingCards));
      for (VisitorParkingCard visitorParkingCard : resident.visitorParkingCards()) {
         assertThat(visitorParkingCard.parkingLotId(), is(parkingLotId));
         assertThat(visitorParkingCard.isAvailable(), is(true));
         assertThat(visitorParkingCard.parkingCardNr(), is(parkingCardNr));
      }
   }
}