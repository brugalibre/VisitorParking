package com.brugalibre.visitorparking.app;

import com.brugalibre.visitorparking.app.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class VisitorParkingManagementRestApplication {

   public static void main(String[] args) {
      Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
      SpringApplication.run(VisitorParkingManagementRestApplication.class, args);
   }
}
