package com.brugalibre.visitorparking.app;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.ParkedCarAdded2FacilityResult;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.domain.model.security.user.User;
import com.brugalibre.visitorparking.domain.model.user.Role;
import com.brugalibre.visitorparking.domain.repository.facility.FacilityRepository;
import com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository;
import com.brugalibre.visitorparking.domain.repository.resident.ResidentRepository;
import com.brugalibre.visitorparking.domain.service.facility.FacilityService;
import com.brugalibre.visitorparking.domain.service.parking.add.ParkCarService;
import com.brugalibre.visitorparking.domain.service.parking.revoke.RevokeParkedCarService;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class VisitorParkingManagementApp {
   private final FacilityService facilityService;
   private final ParkCarService parkCarService;
   private final RevokeParkedCarService parkedCarService;
   private final ParkedCarRepository parkedCarRepository;
   private final ResidentService residentService;
   private final ResidentRepository residentRepository;

   @Autowired
   public VisitorParkingManagementApp(ParkCarService parkCarService, FacilityService facilityService, ParkedCarRepository parkedCarRepository, ResidentRepository residentRepository, ResidentService residentService, RevokeParkedCarService parkedCarService) {
      this.parkCarService = parkCarService;
      this.facilityService = facilityService;
      this.parkedCarRepository = parkedCarRepository;
      this.residentRepository = residentRepository;
      this.residentService = residentService;
      this.parkedCarService = parkedCarService;
   }

   @Transactional
   public void letsgo() {
      FacilityRepository facilityRepository = facilityService.getFacilityRepository();
      Facility facility = facilityService.createFacility("Oristalstrasse 58", ParkingLot.builder()
              .capacity(3)
              .parkedCars(List.of(ParkedCar.builder()
                      .carPlateNo("DG-4")
                      .build()))

              .build());
//      facility = Facility.builder()
//              .id(facility.id())
//              .parkingLot(ParkingLot.builder()
//                      .capacity(3)
//                      .parkedCars(List.of(ParkedCar.builder()
//                              .carPlateNo("DG-4")
//                              .build()))
//                      .build())
//              .build();
//      facility = facilityRepository.save(facility);
      ParkedCarAdded2FacilityResult parkedCarAdded2FacilityResult = facility.addParkedCar(ParkedCar.builder()
              .carPlateNo("DG-2")
              .visitorParkingCard(VisitorParkingCard.builder()
                      .parkingCardNr("asdf")
                      .parkingLotId(facility.parkingLot()
                              .id())
                      .build())
              .build());
      facility = parkedCarAdded2FacilityResult.facility()
              .addResident(Resident.builder()
                      .facility(facility)
                      .visitorParkingCards(List.of(VisitorParkingCard.builder()
                              .parkingCardNr("visitor-1-parking-card")
                              .isAvailable(true)
                              .parkingLotId(facility.parkingLot()
                                      .id())
                              .build()))
                      .user(new User("pw1", "asd1", List.of()))
                      .build())
              .addResident(Resident.builder()
                      .facility(facility)
                      .user(new User("pw2", "asd2", List.of()))
                      .build());
      facility = facilityRepository.save(facility);

      Resident resident = facility.residents().get(0);
      resident = parkCarService.assignParkedCar4PlateNo(resident.id(), "SG-44");
      facility = resident.facility();
      resident = residentRepository.save(resident);
      facility = facilityRepository.save(facility);
      System.err.println(facility);
      System.err.println(resident);

   }

   @Transactional
   public void letsgo1() {

      FacilityRepository facilityRepository = facilityService.getFacilityRepository();

      // Build Facility and ParkingLot
      Facility facilityStr58 = facilityService.createFacility("Oristalstrasse 58", 30);
      Facility facilityStr45 = facilityService.createFacility("Oristalstrasse 45", 40);

      User user = new User("username", "pw", List.of(Role.USER));
      User anotherUser = new User("username1", "pw", List.of(Role.USER));

      Resident resident = residentService.createResident(facilityStr58, user, 2);
      residentService.createResident(facilityStr45, anotherUser, 2);

      String SG_1 = "SG-1";
      String SG_2 = "SG-2";
      String SG_3 = "SG-3";

      resident = parkCarService.assignParkedCar4PlateNo(resident.id(), SG_1);

      facilityStr58 = facilityService.getFacilityRepository().getById(facilityStr58.id());

      log.error("\n\n\n===============================================================");
      log.error("First run");
      log.info(resident.toString());
      log.info(facilityStr58.toString());

      log(facilityRepository, residentRepository, facilityStr58, facilityStr45, SG_1, SG_2, SG_3);

      parkedCarService.revokeParkedCar4PlateNo(resident.id(), SG_1);
      log(facilityRepository, residentRepository, facilityStr58, facilityStr45, SG_1, SG_2, SG_3);
      log.error("Second run");


      log.error("\n===============================================================\n\n\n");
   }

   private void log(FacilityRepository facilityRepository, ResidentRepository residentRepository, Facility facilityStr58, Facility facilityStr45, String SG_1, String SG_2, String SG_3) {
      log.info("Facility {} has total {} parked cars", facilityStr45.name(), facilityStr45.parkingLot().parkedCars().size());
      log.info("Facility {} has total {} residents ", facilityStr45.name(), facilityStr45.residents().size());
      log.info("Facility {} has total {} parked cars", facilityStr58.name(), facilityStr58.parkingLot().parkedCars().size());
      log.info("Facility {} has total {} residents ", facilityStr58.name(), facilityStr58.residents().size());
      log.info("Facility {} {} car assigned for plate {}", facilityStr58.name(), (facilityStr58.hasParkedCarAssignedByPlateNr(SG_1) ? "one" : "no"), SG_1);
      log.info("Facility {} {} car assigned for plate {}", facilityStr58.name(), (facilityStr58.hasParkedCarAssignedByPlateNr(SG_2) ? "one" : "no"), SG_2);
      log.info("Facility {} {} car assigned for plate {}", facilityStr58.name(), (facilityStr58.hasParkedCarAssignedByPlateNr(SG_3) ? "one" : "no"), SG_3);

      log.info("Amount of overall residents: " + residentRepository.getAll().size());
      log.info("Amount of facilities: " + facilityRepository.getAll().size());
      log.info("Amount of parked cars: " + parkedCarRepository.getAll().size());
   }
}
