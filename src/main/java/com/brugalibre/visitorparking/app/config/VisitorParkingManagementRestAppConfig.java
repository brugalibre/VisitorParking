package com.brugalibre.visitorparking.app.config;

import com.brugalibre.visitorparking.app.VisitorParkingManagementApp;
import com.brugalibre.visitorparking.persistence.config.VisitorParkingManagementPersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(VisitorParkingManagementPersistenceConfig.class)
public class VisitorParkingManagementRestAppConfig {

   public static final String VISITOR_PARKING_MANAGEMENT_APP = "VisitorParkingManagementApp";

   @Bean(VISITOR_PARKING_MANAGEMENT_APP)
   public VisitorParkingManagementApp getVisitorParkingManagementApp(@Autowired VisitorParkingManagementApp visitorParkingManagementApp) {
      return visitorParkingManagementApp;
   }
}

