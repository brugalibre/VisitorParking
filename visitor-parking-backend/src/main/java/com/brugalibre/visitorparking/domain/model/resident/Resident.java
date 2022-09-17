package com.brugalibre.visitorparking.domain.model.resident;


import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.ParkedCarAdded2FacilityResult;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;

/**
 * A {@link Resident} defines an inhabitant of a {@link Facility}. Each Resident has a certain amount of {@link VisitorParkingCard}
 * which the resident can give away to any parked car, as long as there are any free parking cards left.
 * <p>
 * Technical a {@link Resident} consist of a list of {@link VisitorParkingCard} and an {@link User}
 */
@Slf4j
public record Resident(String id, Facility facility, String userId,
                       List<VisitorParkingCard> visitorParkingCards) implements DomainModel {
   @Builder
   public Resident {
   }

   @Override
   public String getId() {
      return this.id;
   }

   /**
    * Returns <code>true</code> if the given {@link ParkedCar} is assigned to any {@link VisitorParkingCard} of this {@link Resident}
    * Otherwise <code>false</code> is returned
    *
    * @param carPlateNo the plate number of the parked car to verify
    * @return <code>true</code> if the given {@link ParkedCar} is assigned to any {@link VisitorParkingCard} of this {@link Resident},otherwise <code>false</code> is returned
    */
   public boolean isParkedCarAssigned(String carPlateNo) {
      requireNonNull(carPlateNo, "Plate number of the parked car to check must not be null!");
      return visitorParkingCards.stream()
              .filter(not(VisitorParkingCard::isAvailable))
              .anyMatch(visitorParkingCard -> carPlateNo.equals(visitorParkingCard.parkedCarPlateNo()));
   }

   /**
    * Assigns the next available {@link VisitorParkingCard} of this {@link Resident} to the given {@link ParkedCar}
    * If there was one of the following issues
    * -  This {@link Resident} has no {@link VisitorParkingCard}s left
    * -  the given {@link ParkedCar} has already an assigned {@link VisitorParkingCard}
    * -  No of the {@link VisitorParkingCard} available suites the given {@link ParkingLot}
    * then nothing happens and the {@link AssignedParkedCarResult} has the corresponding values set
    *
    * @param parkedCar the parked car which gets this {@link VisitorParkingCard} assigned
    * @return a {@link AssignedParkedCarResult} which contains the changed {@link Resident} and the {@link ParkedCar}
    */
   public AssignedParkedCarResult addParkedCarAndAssignVisitorParkingCard(ParkedCar parkedCar) {
      requireNonNull(parkedCar, "The parked car must not be null");
      if (isParkedCarAssigned(parkedCar.carPlateNo())) {
         return AssignedParkedCarResult.parkedCarIsAlreadyAssigned(this, parkedCar);
      }
      return this.visitorParkingCards
              .stream()
              .filter(VisitorParkingCard::isAvailable)
              .findFirst()
              .map(assignParkedCardAndReturnResult(parkedCar))
              .orElseGet(() -> AssignedParkedCarResult.areNoVisitorParkingCardsLeft(this, parkedCar));
   }

   /**
    * Un-parks the given {@link ParkedCar} and revokes the {@link VisitorParkingCard} which is currently assigned to it
    *
    * @param parkedCar the parked car which gets un-parked
    * @return a {@link AssignedParkedCarResult} which contains the changed {@link Resident} and the {@link ParkedCar
    * @throws NoVisitorParkingCardsLeftException if there are no {@link VisitorParkingCard}s left
    */
   public RemoveParkedCarResult removeParkedCarAndRevokeVisitorParkingCard(ParkedCar parkedCar) {
      requireNonNull(parkedCar, "The parked car must not be null");
      return this.visitorParkingCards
              .stream()
              .filter(not(VisitorParkingCard::isAvailable))
              .filter(visitorParkingCard -> visitorParkingCard.isAssignedTo(parkedCar))
              .findFirst()
              .map(revokeParkedCardAndReturnResult(parkedCar))
              .orElseGet(() -> new RemoveParkedCarResult(this, parkedCar, true));
   }

   /*
    * First we assign the given VisitorParkingCard to the given parked car. This card is then flagged as unavailable.
    * This parked car is then put into the parking lot of the facility of this resident
    * In the end, we have to update the instance of the resident and return all changed values in a result-struct
    */
   private Function<VisitorParkingCard, AssignedParkedCarResult> assignParkedCardAndReturnResult(ParkedCar parkedCar) {
      return visitorParkingCard -> {
         log.info("Assign visitor parking card {} to parked car {}", visitorParkingCard, parkedCar);
         ParkedCar parkedCarWithCardAssigned = parkedCar.assignVisitorParkingCard(visitorParkingCard);
         ParkedCarAdded2FacilityResult parkedCarAdded2FacilityResult = facility.addParkedCar(parkedCarWithCardAssigned);
         if (parkedCarAdded2FacilityResult.isVisitorParkingCardNotValid()) {
            return AssignedParkedCarResult.isVisitorParkingCardNotValid(this, parkedCar);
         }
         return createAssignedParkedCarResult(visitorParkingCard, parkedCarAdded2FacilityResult);
      };
   }

   /*
    * First we revoke the given VisitorParkingCard from the given parked car. This card is then flagged as available.
    * This parked car is then removed from the parking lot of the facility of this resident
    * In the end, we have to update the instance of the resident and return all changed values in a result-struct
    */
   private Function<VisitorParkingCard, RemoveParkedCarResult> revokeParkedCardAndReturnResult(ParkedCar parkedCar) {
      return visitorParkingCard -> {
         log.info("Revoke visitor parking card {} from parked car {}", visitorParkingCard, parkedCar);
         ParkedCar parkedCarWithCardAssigned = parkedCar.revokeVisitorParkingCard(visitorParkingCard);
         Facility changedFacility = facility.removeParkedCar(parkedCarWithCardAssigned.carPlateNo());
         return createRemoveParkedCarResult(visitorParkingCard, parkedCarWithCardAssigned, changedFacility);
      };
   }

   private AssignedParkedCarResult createAssignedParkedCarResult(VisitorParkingCard visitorParkingCard, ParkedCarAdded2FacilityResult parkedCarAdded2FacilityResult) {
      Resident changedResident = createChangedResident(visitorParkingCard, parkedCarAdded2FacilityResult.parkedCar(), parkedCarAdded2FacilityResult.facility());
      return new AssignedParkedCarResult(changedResident, parkedCarAdded2FacilityResult.parkedCar());
   }

   private RemoveParkedCarResult createRemoveParkedCarResult(VisitorParkingCard visitorParkingCard,
                                                             ParkedCar parkedCarWithCardAssigned, Facility changedFacility) {
      Resident changedResident = createChangedResident(visitorParkingCard, parkedCarWithCardAssigned, changedFacility);
      return new RemoveParkedCarResult(changedResident, parkedCarWithCardAssigned);
   }

   private List<VisitorParkingCard> replaceNewVisitorParkingCard(VisitorParkingCard visitorParkingCard, VisitorParkingCard newVisitorParkingCard) {
      List<VisitorParkingCard> visitorParkingCardsTmp = new ArrayList<>(this.visitorParkingCards);
      visitorParkingCardsTmp.remove(visitorParkingCard);
      visitorParkingCardsTmp.add(newVisitorParkingCard);
      return visitorParkingCardsTmp;
   }

   private Resident createChangedResident(VisitorParkingCard visitorParkingCard, ParkedCar parkedCarWithCardAssigned, Facility changedFacility) {
      return new Resident(this.id, changedFacility, this.userId,
              replaceNewVisitorParkingCard(visitorParkingCard, parkedCarWithCardAssigned.visitorParkingCard()));
   }
}
