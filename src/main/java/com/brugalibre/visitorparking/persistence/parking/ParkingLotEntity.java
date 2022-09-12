package com.brugalibre.visitorparking.persistence.parking;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.sun.istack.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Entity
@Table(name = "parking_lot")
public class ParkingLotEntity extends DomainEntity {

   @NotNull
   private Integer capacity;

   @OneToMany(targetEntity = ParkedCarEntity.class,
           mappedBy = "parkingLot",
           cascade = CascadeType.ALL,
           orphanRemoval = true)
   @LazyCollection(LazyCollectionOption.FALSE)
   @NonNull
   private List<ParkedCarEntity> parkedCars;

   public ParkingLotEntity() {
      super(null);
      this.parkedCars = new ArrayList<>();
   }

   public Integer getCapacity() {
      return capacity;
   }

   public void setCapacity(Integer capacity) {
      this.capacity = capacity;
   }

   @NonNull
   public List<ParkedCarEntity> getParkedCars() {
      return parkedCars;
   }

   public void setParkedCars(@NonNull List<ParkedCarEntity> parkedCarEntities) {
      if (nonNull(parkedCarEntities)) {
         this.parkedCars = new ArrayList<>(parkedCarEntities);
      }
   }
}
