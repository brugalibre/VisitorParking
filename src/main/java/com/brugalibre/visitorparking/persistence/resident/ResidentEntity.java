package com.brugalibre.visitorparking.persistence.resident;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.visitorparking.persistence.facility.FacilityEntity;
import com.brugalibre.visitorparking.persistence.user.UserEntity;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resident", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
public class ResidentEntity extends DomainEntity {

   @OneToMany(targetEntity = VisitorParkingCardEntity.class,
           cascade = CascadeType.ALL,
           fetch = FetchType.EAGER,
           orphanRemoval = true)
   @NonNull
   private List<VisitorParkingCardEntity> visitorParkingCards;

   @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "user_id")
   private UserEntity user;

   @ManyToOne
   @JoinColumn(name = "facility_id")
   private FacilityEntity facility;

   public ResidentEntity() {
      super(null);
      this.visitorParkingCards = new ArrayList<>();
   }

   @NonNull
   public List<VisitorParkingCardEntity> getVisitorParkingCards() {
      return visitorParkingCards;
   }

   public void setVisitorParkingCards(@NonNull List<VisitorParkingCardEntity> visitorParkingCards) {
      this.visitorParkingCards = visitorParkingCards;
   }

   public UserEntity getUser() {
      return user;
   }

   public void setUser(UserEntity user) {
      this.user = user;
   }

   public FacilityEntity getFacility() {
      return facility;
   }

   public void setFacility(FacilityEntity facility) {
      this.facility = facility;
   }
}
