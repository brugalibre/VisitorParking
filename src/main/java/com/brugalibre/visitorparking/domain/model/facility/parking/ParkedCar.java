package com.brugalibre.visitorparking.domain.model.facility.parking;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a parked car within the parking lot
 */
@Slf4j
public record ParkedCar(String id, String carPlateNo, VisitorParkingCard visitorParkingCard) implements DomainModel {
   @Builder
   public ParkedCar {
   }

   /**
    * Assigns the given {@link VisitorParkingCard} to this {@link ParkedCar}.
    * The {@link VisitorParkingCard} assigned to this {@link ParkedCar} is therefore flagged as unavailable.
    *
    * @param visitorParkingCard the {@link VisitorParkingCard} to set
    * @return a new instance of a {@link ParkedCar}
    */
   public ParkedCar assignVisitorParkingCard(VisitorParkingCard visitorParkingCard) {
      log.info("Set visitor-parking card {} to parked car {}", this.visitorParkingCard, this);
      return new ParkedCar(this.id, this.carPlateNo, visitorParkingCard.assignToParkedCar(this.carPlateNo));
   }

   /**
    * Revokes the given {@link VisitorParkingCard} from this {@link ParkedCar}.
    * The {@link VisitorParkingCard} revoked from  this {@link ParkedCar} is therefore flagged as available.
    *
    * @param visitorParkingCard the {@link VisitorParkingCard} to set
    * @return a new instance of a {@link ParkedCar}
    */
   public ParkedCar revokeVisitorParkingCard(VisitorParkingCard visitorParkingCard) {
      log.info("Revoke visitor-parking card {} from parked car {}", this.visitorParkingCard, this);
      return new ParkedCar(this.id, this.carPlateNo, visitorParkingCard.flagAsAvailable());
   }

   @Override
   public String getId() {
      return this.id;
   }
}
