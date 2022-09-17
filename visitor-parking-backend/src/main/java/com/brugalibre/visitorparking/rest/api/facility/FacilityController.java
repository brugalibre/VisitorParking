package com.brugalibre.visitorparking.rest.api.facility;


import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.model.facility.Facility;
import com.brugalibre.visitorparking.domain.repository.facility.FacilityRepository;
import com.brugalibre.visitorparking.rest.api.resident.ResidentControllerService;
import com.brugalibre.visitorparking.rest.model.facility.FacilityDto;
import com.brugalibre.visitorparking.rest.model.facility.mapper.FacilityDtoMapper;
import com.brugalibre.visitorparking.rest.model.facility.mapper.FacilityDtoMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/visitor-parking-management")
@RestController
public class FacilityController {

   private final ResidentControllerService residentControllerService;
   private final FacilityRepository facilityRepository;
   private final FacilityDtoMapper facilityDtoMapper;

   @Autowired
   public FacilityController(FacilityRepository facilityRepository, ResidentControllerService residentControllerService) {
      this.residentControllerService = residentControllerService;
      this.facilityRepository = facilityRepository;
      this.facilityDtoMapper = new FacilityDtoMapperImpl();
   }

   /**
    * Returns the id for a {@link FacilityDto} which belongs to the given user id
    *
    * @param userId the id of a {@link User}
    * @return the id for a {@link FacilityDto} which belongs to the given user id
    */
   @GetMapping(path = "/facilityId4UserId/{userId}")
   public String getFacilityId4UserId(@PathVariable String userId) {
      return residentControllerService.getResidentDto(userId).facilityId();
   }

   /**
    * Returns a {@link FacilityDto} for the given id
    *
    * @param facilityId the id of a {@link Facility}
    * @return a {@link FacilityDto} for the given id
    */
   @GetMapping(path = "/facility/{facilityId}")
   public FacilityDto getFacility(@PathVariable String facilityId) {
      return getFacilityDto(facilityId);
   }

   private FacilityDto getFacilityDto(String facilityId) {
      Facility facility = this.facilityRepository.getById(facilityId);
      return this.facilityDtoMapper.map2FacilityDto(facility);
   }
}
