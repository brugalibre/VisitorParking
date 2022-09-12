package com.brugalibre.visitorparking.persistence.parking;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import com.brugalibre.visitorparking.persistence.resident.VisitorParkingCardEntity;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "parked_car", uniqueConstraints = {@UniqueConstraint(columnNames = "carPlateNo")})
public class ParkedCarEntity extends DomainEntity {
   @NonNull
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