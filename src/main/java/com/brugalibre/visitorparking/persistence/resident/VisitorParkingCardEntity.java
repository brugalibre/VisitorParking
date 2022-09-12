package com.brugalibre.visitorparking.persistence.resident;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitor_parking_card", uniqueConstraints = {@UniqueConstraint(columnNames = "parkingCardNr")})
public class VisitorParkingCardEntity extends DomainEntity {

   @NotNull
   private String parkingCardNr;

   @NotNull
   private String parkingLotId;

   private String parkedCarPlateNo;

   private boolean isAvailable;

   private LocalDateTime assignedSince;

   public VisitorParkingCardEntity() {
      super(null);
   }

   public String getParkingCardNr() {
      return parkingCardNr;
   }

   public void setParkingCardNr(String parkingCardNr) {
      this.parkingCardNr = parkingCardNr;
   }

   public boolean getIsAvailable() {
      return isAvailable;
   }

   public void setIsAvailable(boolean available) {
      isAvailable = available;
   }

   public String getParkingLotId() {
      return parkingLotId;
   }

   public void setParkingLotId(String parkingLotId) {
      this.parkingLotId = parkingLotId;
   }

   public String getParkedCarPlateNo() {
      return parkedCarPlateNo;
   }

   public void setParkedCarPlateNo(String parkedCarPlateNo) {
      this.parkedCarPlateNo = parkedCarPlateNo;
   }

   public LocalDateTime getAssignedSince() {
      return assignedSince;
   }

   public void setAssignedSince(LocalDateTime assignedSince) {
      this.assignedSince = assignedSince;
   }
}
