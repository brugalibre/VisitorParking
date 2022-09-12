package com.brugalibre.visitorparking.app;

import com.brugalibre.visitorparking.app.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static com.brugalibre.visitorparking.app.config.VisitorParkingManagementRestAppConfig.VISITOR_PARKING_MANAGEMENT_APP;

@SpringBootApplication
public class VisitorParkingManagementRestApplication {
   public static void main(String[] args) {
      Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
      ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(VisitorParkingManagementRestApplication.class, args);
      VisitorParkingManagementApp aquabasileaCourseBooker = (VisitorParkingManagementApp) configurableApplicationContext.getBean(VISITOR_PARKING_MANAGEMENT_APP);
      aquabasileaCourseBooker.letsgo();
   }
}
