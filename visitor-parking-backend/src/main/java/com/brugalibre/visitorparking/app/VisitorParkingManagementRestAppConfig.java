package com.brugalibre.visitorparking.app;

import com.brugalibre.common.domain.app.config.CommonAppPersistenceConfig;
import com.brugalibre.common.security.app.config.CommonAppSecurityConfig;
import com.brugalibre.common.security.rest.service.UserRegisterService;
import com.brugalibre.visitorparking.app.config.VisitorParkingManagementPersistenceConfig;
import com.brugalibre.visitorparking.domain.service.facility.DefaultFacilityService;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import com.brugalibre.visitorparking.rest.api.security.VisitorParkingUserRegisteredObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"com.brugalibre.visitorparking"})
@Configuration
@Import({VisitorParkingManagementPersistenceConfig.class, CommonAppSecurityConfig.class, CommonAppPersistenceConfig.class})
public class VisitorParkingManagementRestAppConfig {

   private static final String VISITOR_PARKING_USER_REGISTERED_OBSERVER = "VisitorParkingUserRegisteredObserver";

   @Bean(name = VISITOR_PARKING_USER_REGISTERED_OBSERVER)
   public VisitorParkingUserRegisteredObserver getVisitorParkingUserRegisteredObserver(@Autowired DefaultFacilityService facilityService,
                                                                                       @Autowired ResidentService residentService,
                                                                                       @Autowired UserRegisterService userRegisterService) {
      VisitorParkingUserRegisteredObserver visitorParkingUserRegisteredObserver =
              new VisitorParkingUserRegisteredObserver(facilityService, residentService);
      userRegisterService.addUserRegisteredObserver(visitorParkingUserRegisteredObserver);
      return visitorParkingUserRegisteredObserver;
   }
}

