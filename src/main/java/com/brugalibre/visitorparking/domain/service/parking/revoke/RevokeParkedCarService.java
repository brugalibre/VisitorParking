package com.brugalibre.visitorparking.domain.service.parking.revoke;

import com.brugalibre.visitorparking.domain.model.facility.parking.ParkedCar;
import com.brugalibre.visitorparking.domain.model.resident.*;
import com.brugalibre.visitorparking.domain.repository.facility.FacilityRepository;
import com.brugalibre.visitorparking.domain.repository.facility.ParkedCarRepository;
import com.brugalibre.visitorparking.domain.repository.resident.ResidentRepository;
import com.brugalibre.visitorparking.domain.service.parking.revoke.exception.NoVisitorParkingCardAssignedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@link RevokeParkedCarService} contains the logic for assigning a {@link ParkedCar} to a {@link Resident}s {@link VisitorParkingCard}
 */
@Service
public class RevokeParkedCarService {

   private final ResidentRepository residentRepository;
   private final ParkedCarRepository parkedCarRepository;
   private final FacilityRepository facilityRepository;

   @Autowired
   public RevokeParkedCarService(ResidentRepository residentRepository, FacilityRepository facilityRepository, ParkedCarRepository parkedCarRepository) {
      this.facilityRepository = facilityRepository;
      this.residentRepository = residentRepository;
      this.parkedCarRepository = parkedCarRepository;
   }

   /**
    * This method undoes a previously assigned parked car with the given plate-number to the resident with the given id.
    *
    * @param residentId the id of the resident
    * @param carPlateNo the plate-no of the parked car to revoke the previously assigned {@link VisitorParkingCard}
    * @return an updated instance of the {@link Resident} for the given id
    * @throws NoVisitorParkingCardAssignedException if there is no {@link VisitorParkingCard} assigned to a
    *                                               {@link ParkedCar} with the given plate no
    */
   public Resident revokeParkedCar4PlateNo(String residentId, String carPlateNo) {
      Resident resident = residentRepository.getById(residentId);
      ParkedCar parkedCar = parkedCarRepository.getByCarPlateNo(carPlateNo);
      RemoveParkedCarResult removeParkedCarResult = resident.removeParkedCarAndRevokeVisitorParkingCard(parkedCar);
      return verifyAndPersistChangesAndReturnResident(removeParkedCarResult);
   }

   private Resident verifyAndPersistChangesAndReturnResident(RemoveParkedCarResult removeParkedCarResult) {
      verifyRemoveParkedCarResult(removeParkedCarResult);
      return persistChangesAndReturnResident(removeParkedCarResult.resident());
   }

   private Resident persistChangesAndReturnResident(Resident resident) {
      residentRepository.save(resident);
      facilityRepository.save(resident.facility());
      return residentRepository.getById(resident.getId());
   }

   private static void verifyRemoveParkedCarResult(RemoveParkedCarResult removeParkedCarResult) {
      if (removeParkedCarResult.hasNoVisitorParkingCardAssigned()) {
         throw new NoVisitorParkingCardAssignedException(removeParkedCarResult.resident(), removeParkedCarResult.parkedCar());
      }
   }
}
