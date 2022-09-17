package com.brugalibre.visitorparking.rest.api.resident;

import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.rest.model.resident.ResidentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/visitor-parking-management")
@RestController
public class ResidentController {

   private final ResidentControllerService residentControllerService;

   @Autowired
   public ResidentController(ResidentControllerService residentControllerService) {
      this.residentControllerService = residentControllerService;
   }

   /**
    * Returns the {@link ResidentDto} associated to the given user id
    *
    * @param userId the id uf a {@link User} to find the resident
    * @return the {@link ResidentDto} associated to the given id
    * @throws NoDomainModelFoundException if there is no {@link ResidentDto} associated with the given id
    */
   @GetMapping(path = "/resident/{userId}")
   public ResidentDto getResident(@PathVariable String userId) {
      return residentControllerService.getResidentDto(userId);
   }
}