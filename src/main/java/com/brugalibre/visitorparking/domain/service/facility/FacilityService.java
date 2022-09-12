package com.brugalibre.visitorparking.domain.service.facility;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.repository.facility.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacilityService {

   private final FacilityRepository facilityRepository;

   @Autowired
   public FacilityService(FacilityRepository facilityRepository) {
      this.facilityRepository = facilityRepository;
   }

   /**
    * Creates and persists a new {@link Facility} with the given name. It also creates a new parking lot
    *
    * @param facilityName       the name of the {@link Facility}
    * @param parkingLotCapacity the capacity of the {@link ParkingLot} of this {@link Facility}
    * @return a new created {@link Facility} instance with the given name and with the given {@link ParkingLot}
    */
   public Facility createFacility(String facilityName, int parkingLotCapacity) {
      return createFacility(facilityName, ParkingLot.builder()
              .capacity(parkingLotCapacity)
              .build());
   }

   /**
    * Creates and persists a new {@link Facility} with the given name. It uses the proved {@link ParkingLot} for
    * parking cars. This {@link ParkingLot} can safely be used by multiple {@link Facility}
    *
    * @param facilityName the name of the facility
    * @param parkingLot   the parking lot the parking lot of this facility
    * @return a new created facility instance with the given name and with the given parking lot
    */
   public Facility createFacility(String facilityName, ParkingLot parkingLot) {
      Facility facility = Facility.builder()
              .name(facilityName)
              .parkingLot(parkingLot)
              .build();
      return facilityRepository.save(facility);
   }

   public FacilityRepository getFacilityRepository() {
      return facilityRepository;
   }
}
