package com.brugalibre.visitorparking.domain.model.resident.builder;

import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The {@link ResidentBuilder} is responsible for building a {@link Resident}
 */
public class ResidentBuilder {

   private final Supplier<String> visitorParkingNoGenerator;

   public ResidentBuilder(Supplier<String> visitorParkingNoGenerator) {
      this.visitorParkingNoGenerator = visitorParkingNoGenerator;
   }

   /**
    * Creates and persists a new {@link Resident} which belongs to the given {@link Facility} and the given technical user
    *
    * @param facility the facility the new resident will belong to
    * @param user     the technical user behind this resident
    * @return a new {@link Resident} instance
    */
   public Resident buildResident(Facility facility, User user, int amountOfVisitorParkingCards) {
      return Resident.builder()
              .facility(facility)
              .visitorParkingCards(buildVisitorParkingCards(amountOfVisitorParkingCards, facility.parkingLot().getId()))
              .userId(user.id())
              .build();
   }

   private List<VisitorParkingCard> buildVisitorParkingCards(int amountOfVisitorParkingCards, String parkingLotId) {
      List<VisitorParkingCard> visitorParkingCards = new ArrayList<>();
      for (int i = 0; i < amountOfVisitorParkingCards; i++) {
         VisitorParkingCard visitorParkingCard = VisitorParkingCard.builder()
                 .isAvailable(true)
                 .parkingLotId(parkingLotId)
                 .parkingCardNr(visitorParkingNoGenerator.get())
                 .build();
         visitorParkingCards.add(visitorParkingCard);
      }
      return visitorParkingCards;
   }
}
