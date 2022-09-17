package com.brugalibre.visitorparking.rest.api.security;

import com.brugalibre.common.security.auth.register.UserRegisteredEvent;
import com.brugalibre.common.security.auth.register.UserRegisteredObserver;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.service.facility.DefaultFacilityService;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import org.springframework.transaction.annotation.Transactional;

public class VisitorParkingUserRegisteredObserver implements UserRegisteredObserver {

   private final DefaultFacilityService defaultFacilityService;
   private final ResidentService residentService;

   public VisitorParkingUserRegisteredObserver(DefaultFacilityService defaultFacilityService, ResidentService residentService) {
      this.residentService = residentService;
      this.defaultFacilityService = defaultFacilityService;
   }

   @Override
   @Transactional
   public void userRegistered(UserRegisteredEvent userRegisteredEvent) {
      User user = User.of(userRegisteredEvent.userId(), userRegisteredEvent.username(), "", "");
      residentService.createResident(defaultFacilityService.getDefaultFacility(), user, 2);
   }
}
