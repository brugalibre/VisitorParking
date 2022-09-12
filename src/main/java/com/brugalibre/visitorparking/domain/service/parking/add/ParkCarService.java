package com.brugalibre.visitorparking.domain.service.parking.add;

import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.facility.parking.ParkingLot;
import com.brugalibre.visitorparking.domain.model.resident.AssignedParkedCarResult;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.NoVisitorParkingCardsLeftException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.ParkedCarAlreadyAssignedToVisitorParkingCardException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.VisitorParkingCardNotValidException;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@link ParkCarService} contains the logic for assigning a {@link ParkedCar}
 * to a {@link Resident}s {@link VisitorParkingCard}
 */
@Service
public class ParkCarService {

   private final ResidentService residentService;
   private final ParkedCarRepository parkedCarRepository;

   @Autowired
   public ParkCarService(ResidentService residentService, ParkedCarRepository parkedCarRepository) {
      this.residentService = residentService;
      this.parkedCarRepository = parkedCarRepository;
   }

   /**
    * Returns <code>true</code> if there exists {@link ParkedCar} for the given plate number which
    * is assigned to any {@link VisitorParkingCard} of a {@link Resident} for the given id.
    * Otherwise <code>false</code> is returned
    *
    * @param residentId the id of the {@link Resident} which is verified
    * @param carPlateNo the plate number of the parked car to verify
    * @return <code>true</code> if there exists {@link ParkedCar} for the given plate number which
    * is assigned to any {@link VisitorParkingCard} of a {@link Resident} for the given id, otherwise <code>false</code> is returned
    */
   public boolean hasParkedCarAssignedByPlateNo(String residentId, String carPlateNo) {
      Resident resident = residentService.getById(residentId);
      return resident.hasParkedCarAssigned(carPlateNo);
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
   public Resident assignParkedCar4PlateNo(String residentId, String carPlateNo) {
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
      if (assignedParkedCarResult.hasNoVisitorParkingCardsLeft()) {
         throw new NoVisitorParkingCardsLeftException(assignedParkedCarResult.resident());
      } else if (assignedParkedCarResult.isVisitorParkingCardNotValid()) {
         throw new VisitorParkingCardNotValidException(assignedParkedCarResult.parkedCar());
      } else if (assignedParkedCarResult.hasParkedCarAlreadyVisitorCardAssigned()) {
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
    * @return an existing and persited {@link ParkedCar}
    */
   private ParkedCar getParkedCar(AssignedParkedCarResult assignedParkedCarResult) {
      return parkedCarRepository.getByCarPlateNo(assignedParkedCarResult.parkedCar().carPlateNo());
   }
}
