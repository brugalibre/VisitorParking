package com.brugalibre.visitorparking.rest.api.resident;

import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.service.resident.ResidentService;
import com.brugalibre.visitorparking.rest.model.resident.ResidentDto;
import com.brugalibre.visitorparking.rest.model.resident.mapper.ResidentDtoMapper;
import com.brugalibre.visitorparking.rest.model.resident.mapper.ResidentDtoMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentControllerService {

   private final UserRepository userRepository;
   private final ResidentService residentService;
   private final ResidentDtoMapper residentDtoMapper;

   @Autowired
   public ResidentControllerService(ResidentService residentService, UserRepository userRepository) {
      this.userRepository = userRepository;
      this.residentService = residentService;
      this.residentDtoMapper = new ResidentDtoMapperImpl();
   }

   /**
    * Returns the {@link ResidentDto} associated to the given user id
    *
    * @param userId the id uf a {@link User} to find the resident
    * @return the {@link ResidentDto} associated to the given id
    * @throws NoDomainModelFoundException if there is no {@link ResidentDto} associated with the given id
    */
   public ResidentDto getResidentDto(String userId) {
      Resident resident = this.residentService.getByUserId(userId);
      User user = userRepository.getById(resident.userId());
      return this.residentDtoMapper.map2ResidentDto(resident, user);
   }
}