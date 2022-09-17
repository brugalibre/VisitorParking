package com.brugalibre.visitorparking.domain.service.resident;

import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.resident.builder.ResidentBuilder;
import com.brugalibre.visitorparking.domain.repository.facility.FacilityRepository;
import com.brugalibre.visitorparking.domain.repository.resident.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The {@link ResidentService} contains convenient logic for creating and retrieving a {@link Resident}
 */
@Service
public class ResidentService {

   private final ResidentBuilder residentBuilder;
   private final ResidentRepository residentRepository;
   private final FacilityRepository facilityRepository;

   @Autowired
   public ResidentService(ResidentRepository residentRepository, FacilityRepository facilityRepository) {
      this.facilityRepository = facilityRepository;
      this.residentRepository = residentRepository;
      this.residentBuilder = new ResidentBuilder(() -> UUID.randomUUID().toString());
   }

   /**
    * Creates and persists a new {@link Resident} which belongs to the given {@link Facility} and the given technical user
    *
    * @param facility the facility the new resident will belong to
    * @param user     the technical user behind this resident
    * @return a new {@link Resident} instance
    */
   public Resident createResident(Facility facility, User user, int amountOfVisitorParkingCards) {
      Resident resident = residentBuilder.buildResident(facility, user, amountOfVisitorParkingCards);
      resident = residentRepository.save(resident);
      facilityRepository.save(resident.facility().addResident(resident));
      return residentRepository.getById(resident.getId());// get again, in order to retrieve the persistent facility
   }

   /**
    * Persist the changes of the given {@link Resident} and it's {@link Facility} and
    * reloads the changed {@link Resident} from the database
    *
    * @param resident the resident to change
    * @return a changed instance of the resident
    */
   public Resident persistAndReloadResident(Resident resident) {
      Resident persistentResident = residentRepository.save(resident);
      facilityRepository.save(resident.facility());
      return residentRepository.getById(persistentResident.getId());
   }

   /**
    * Returns the {@link Resident} associated to the given id
    *
    * @param residentId the id to find the resident
    * @return the {@link Resident} associated to the given id
    * @throws NoDomainModelFoundException if there is no {@link Resident} associated with the given id
    */
   public Resident getById(String residentId) {
      return this.residentRepository.getById(residentId);
   }

   /**
    * Returns the {@link Resident} associated to the given user id
    *
    * @param userId the id uf a {@link User} to find the resident
    * @return the {@link Resident} associated to the given id
    * @throws NoDomainModelFoundException if there is no {@link Resident} associated with the given id
    */
   public Resident getByUserId(String userId) {
      return this.residentRepository.getByUserId(userId);
   }
}
