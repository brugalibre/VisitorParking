package com.brugalibre.visitorparking.persistence.parking;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.visitorparking.persistence.resident.VisitorParkingCardEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "parked_car", uniqueConstraints = {@UniqueConstraint(columnNames = "carPlateNo")})
public class ParkedCarEntity extends DomainEntity {

   private static final String CAR_PLATE_NO_PATTERN = "(^[A-Z]{2}\\s[0-9]{1,6}$)";

   @Pattern(regexp = CAR_PLATE_NO_PATTERN, message = "Wrong car plate number format!")
   @NotNull
   private String carPlateNo;

   @ManyToOne
   @JoinColumn(name = "parking_lot_id")
   private ParkingLotEntity parkingLot;

   @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "visitor_parking_card_id")
   private VisitorParkingCardEntity visitorParkingCard;

   public ParkedCarEntity() {
      super(null);
   }

   public String getCarPlateNo() {
      return carPlateNo;
   }

   public void setCarPlateNo(String carPlateNo) {
      this.carPlateNo = carPlateNo;
   }

   public VisitorParkingCardEntity getVisitorParkingCard() {
      return visitorParkingCard;
   }

   public void setVisitorParkingCard(VisitorParkingCardEntity visitorParkingCard) {
      this.visitorParkingCard = visitorParkingCard;
   }

   public ParkingLotEntity getParkingLot() {
      return parkingLot;
   }

   public void setParkingLot(ParkingLotEntity parkingLot) {
      this.parkingLot = parkingLot;
   }
}