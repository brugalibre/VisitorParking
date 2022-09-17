package com.brugalibre.visitorparking.domain.service.parking.add;

import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.resident.AssignedParkedCarResult;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository;
import com.brugalibre.visitorparking.domain.service.facility.FacilityService;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.NoVisitorParkingCardsLeftException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.ParkedCarAlreadyAssignedToVisitorParkingCardException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.VisitorParkingCardNotValidException;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

/**
 * The {@link ParkCarService} contains the logic for assigning a {@link ParkedCar}
 * to a {@link Resident}s {@link VisitorParkingCard}
 */
@Service
public class ParkCarService {

   private final FacilityService facilityService;
   private final UserRepository userRepository;
   private final ResidentService residentService;
   private final ParkedCarRepository parkedCarRepository;

   @Autowired
   public ParkCarService(ResidentService residentService, ParkedCarRepository parkedCarRepository,
                         FacilityService facilityService, UserRepository userRepository) {
      this.residentService = residentService;
      this.userRepository = userRepository;
      this.facilityService = facilityService;
      this.parkedCarRepository = parkedCarRepository;
   }

   /**
    * Returns <code>true</code> if there exists {@link ParkedCar} for the given {@link Facility}-id and for the given plate number which
    * is assigned to any {@link VisitorParkingCard}. Otherwise <code>false</code> is returned
    *
    * @param facilityId the id of the {@link Facility} which is verified
    * @param carPlateNo the plate number of the parked car to verify
    * @return <code>true</code> if there exists {@link ParkedCar} for the given plate number which
    * is assigned to any {@link VisitorParkingCard} of a {@link Resident} for the given id, otherwise <code>false</code> is returned
    */
   public boolean isParkedCarAssignedByPlateNo(String facilityId, String carPlateNo) {
      Facility facility = facilityService.getById(facilityId);
      return facility.isParkedCarAssignedByPlateNr(carPlateNo);
   }

   /**
    * Assigns a parked car with the given plate-number to the resident with the given id.
    * Therefore, this method creates a new {@link ParkedCar} and places it on the {@link ParkingLot} of the residents {@link Facility}
    *
    * @param residentId the id of the resident
    * @param carPlateNo the plate number of the parked car
    * @return an updated instance of the {@link Resident} for the given id
    * @throws NoVisitorParkingCardsLeftException                    if the {@link Resident} for the given id has no available {@link VisitorParkingCard}s left
    * @throws VisitorParkingCardNotValidException                   if the {@link Resident} for the given id has no valid {@link VisitorParkingCard}s to park the given car
    * @throws ParkedCarAlreadyAssignedToVisitorParkingCardException if the car to park has already an {@link VisitorParkingCard} assigned
    */
   @Transactional
   public Resident assignParkedCar4PlateNo(String residentId, String carPlateNo) {
      validateInputs(residentId, carPlateNo);
      Resident resident = residentService.getById(residentId);

      // Create new parked car, park it on the parking lot ad assign visitor-card
      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo(carPlateNo)
              .build();
      AssignedParkedCarResult assignedParkedCarResult = resident.addParkedCarAndAssignVisitorParkingCard(parkedCar);
      return verifyAndPersistChangesAndReturnResident(assignedParkedCarResult);
   }

   private Resident verifyAndPersistChangesAndReturnResident(AssignedParkedCarResult assignedParkedCarResult) {
      verifyAssignedParkedCarResult(assignedParkedCarResult);
      return this.residentService.persistAndReloadResident(assignedParkedCarResult.resident());
   }

   private void verifyAssignedParkedCarResult(AssignedParkedCarResult assignedParkedCarResult) {
      if (assignedParkedCarResult.areNoVisitorParkingCardsLeft()) {
         User user = userRepository.getById(assignedParkedCarResult.resident().userId());
         throw new NoVisitorParkingCardsLeftException(user.username());
      } else if (assignedParkedCarResult.isVisitorParkingCardNotValid()) {
         throw new VisitorParkingCardNotValidException(assignedParkedCarResult.parkedCar());
      } else if (assignedParkedCarResult.isParkedCarAlreadyVisitorCardAssigned()) {
         ParkedCar parkedCar = getParkedCar(assignedParkedCarResult);
         throw new ParkedCarAlreadyAssignedToVisitorParkingCardException(parkedCar);
      }
   }

   /**
    * We have to reload the parked car since the {@link ParkedCar} provided from the outside does not have the full information
    * about an already assigned {@link VisitorParkingCard} and so on.
    * But since there already exist a {@link ParkedCar} we can safely load it from the db
    *
    * @param assignedParkedCarResult the {@link AssignedParkedCarResult}
    * @return an existing and persisted {@link ParkedCar}
    */
   private ParkedCar getParkedCar(AssignedParkedCarResult assignedParkedCarResult) {
      return parkedCarRepository.getByCarPlateNo(assignedParkedCarResult.parkedCar().carPlateNo());
   }

   private static void validateInputs(String residentId, String carPlateNo) {
      requireNonNull(residentId, "we need a resident");
      requireNonNull(carPlateNo, "we need a car plate number");
   }
}
