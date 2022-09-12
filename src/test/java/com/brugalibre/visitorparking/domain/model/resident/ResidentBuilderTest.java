package com.brugalibre.visitorparking.domain.model.resident;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.security.user.User;
import com.brugalibre.visitorparking.domain.model.user.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

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
      User user = new User("username", "pw", List.of(Role.USER));
      int amountOfVisitorParkingCards = 4;

      // When
      Resident resident = residentBuilder.buildResident(facility, user, amountOfVisitorParkingCards);

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