package com.brugalibre.visitorparking.persistence.resident;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resident", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
public class ResidentEntity extends DomainEntity {

   @OneToMany(targetEntity = VisitorParkingCardEntity.class,
           cascade = CascadeType.ALL,
           fetch = FetchType.EAGER,
           orphanRemoval = true)
   @NotNull
   private List<VisitorParkingCardEntity> visitorParkingCards;

   @NotNull
   @Column(name = "user_id")
   private String userId;

   @ManyToOne
   @JoinColumn(name = "facility_id")
   private FacilityEntity facility;

   public ResidentEntity() {
      super(null);
      this.visitorParkingCards = new ArrayList<>();
   }

   @NotNull
   public List<VisitorParkingCardEntity> getVisitorParkingCards() {
      return visitorParkingCards;
   }

   public void setVisitorParkingCards(@NotNull List<VisitorParkingCardEntity> visitorParkingCards) {
      this.visitorParkingCards = visitorParkingCards;
   }

   @NotNull
   public String getUserId() {
      return userId;
   }

   public void setUserId(@NotNull String userId) {
      this.userId = userId;
   }

   public FacilityEntity getFacility() {
      return facility;
   }

   public void setFacility(FacilityEntity facility) {
      this.facility = facility;
   }
}
