package com.brugalibre.visitorparking.persistence.config;


import com.brugalibre.visitorparking.persistence.facility.FacilityDao;
import com.brugalibre.visitorparking.persistence.parking.ParkedCarDao;
import com.brugalibre.visitorparking.persistence.parking.ParkingLotDao;
import com.brugalibre.visitorparking.persistence.resident.ResidentDao;
import com.brugalibre.visitorparking.persistence.user.UserDao;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {FacilityDao.class, ParkedCarDao.class, ParkingLotDao.class,
        ResidentDao.class, UserDao.class})
@EntityScan(basePackages = {"com.brugalibre.visitorparking.persistence"})
@ComponentScan(basePackages = {"com.brugalibre.visitorparking"})
public class VisitorParkingManagementPersistenceConfig {
}
