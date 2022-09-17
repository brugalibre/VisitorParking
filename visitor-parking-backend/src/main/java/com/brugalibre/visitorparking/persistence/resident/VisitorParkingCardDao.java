package com.brugalibre.visitorparking.persistence.resident;


import org.springframework.data.repository.CrudRepository;

public interface VisitorParkingCardDao extends CrudRepository<VisitorParkingCardEntity, String> {
   // no-op
}
