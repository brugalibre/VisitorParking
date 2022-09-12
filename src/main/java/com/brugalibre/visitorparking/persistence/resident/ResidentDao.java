package com.brugalibre.visitorparking.persistence.resident;


import org.springframework.data.repository.CrudRepository;

public interface ResidentDao extends CrudRepository<ResidentEntity, String> {
   // no-op
}
