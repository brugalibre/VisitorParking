package com.brugalibre.visitorparking.domain.service.facility;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DefaultFacilityService {

   @Value("${application.facility.defaultFacilityName}")
   private String defaultFacilityName;
   @Value("${application.facility.parkingLotCapacity}")
   private int parkingLotCapacity;

   private final FacilityService facilityService;
   private Facility defaultFacility;

   @Autowired
   public DefaultFacilityService(FacilityService facilityService) {
      this.facilityService = facilityService;
   }

   @EventListener
   public void onApplicationEvent(final ServletWebServerInitializedEvent event) {
      this.defaultFacility = facilityService.createFacility(defaultFacilityName, parkingLotCapacity);
   }

   public Facility getDefaultFacility() {
      return defaultFacility;
   }
}
