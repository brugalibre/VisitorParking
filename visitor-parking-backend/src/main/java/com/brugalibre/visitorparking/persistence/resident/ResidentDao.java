package com.brugalibre.visitorparking.persistence.resident;


import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.visitorparking.domain.model.resident.Resident;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface ResidentDao extends CrudRepository<ResidentEntity, String> {

   /**
    * Returns the {@link Resident} associated to the given user id
    *
    * @param userId the id uf a {@link User} to find the resident
    * @return the {@link Resident} associated to the given id
    * @throws NoDomainModelFoundException if there is no {@link Resident} associated with the given id
    */
   @NotNull
   ResidentEntity getByUserId(@NotNull String userId);
}
