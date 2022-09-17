package com.brugalibre.visitorparking.persistence.facility;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.visitorparking.persistence.parking.ParkingLotEntity;
import com.brugalibre.visitorparking.persistence.resident.ResidentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Entity
@Table(name = "facility", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class FacilityEntity extends DomainEntity {

   @NotNull
   private String name;

   @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "parking_lot_id")
   private ParkingLotEntity parkingLot;

   @OneToMany(targetEntity = ResidentEntity.class,
           mappedBy = "facility",
           cascade = CascadeType.ALL)
   @LazyCollection(LazyCollectionOption.FALSE)
   @NotNull
   private List<ResidentEntity> residents;

   public FacilityEntity() {
      super(null);
      this.residents = new ArrayList<>();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ParkingLotEntity getParkingLot() {
      return parkingLot;
   }

   public void setParkingLot(ParkingLotEntity parkingLot) {
      this.parkingLot = parkingLot;
   }

   @NotNull
   public List<ResidentEntity> getResidents() {
      return residents;
   }

   public void setResidents(List<ResidentEntity> residents) {
      if (nonNull(residents)) {
         this.residents = new ArrayList<>(residents);
      }
   }
}