package com.brugalibre.visitorparking.persistence.facility;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.visitorparking.persistence.parking.ParkingLotEntity;
import com.brugalibre.visitorparking.persistence.resident.ResidentEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Entity
@Table(name = "facility", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class FacilityEntity extends DomainEntity {

   @NonNull
   private String name;

   @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "parking_lot_id")
   private ParkingLotEntity parkingLot;

   @OneToMany(targetEntity = ResidentEntity.class,
           mappedBy = "facility",
           cascade = CascadeType.ALL)
   @LazyCollection(LazyCollectionOption.FALSE)
   @NonNull
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

   @NonNull
   public List<ResidentEntity> getResidents() {
      return residents;
   }

   public void setResidents(@NonNull List<ResidentEntity> residents) {
      if (nonNull(residents)) {
         this.residents = new ArrayList<>(residents);
      }
   }
}