package com.brugalibre.common.domain.repository;

import com.brugalibre.common.domain.model.DomainModel;

/**
 * Base class for all domain repositories
 *
 * @param <T> type of the {@link DomainModel} this repository obtains
 */
public interface CommonDomainRepository<T extends DomainModel> {
   /**
    * Saves the given {@link DomainModel} to the database and returns an updated version of itself
    *
    * @param domainModel the domain object to save
    * @return a persisted or updated version of the given {@link DomainModel}
    */
   T save(T domainModel);

   /**
    * Get the {@link DomainModel} for the given id
    *
    * @param id the id to find the domain model
    * @return the found {@link DomainModel}
    * @throws NoDomainModelFoundException if there is no {@link DomainModel} associated to the given key
    */
   T getById(String id);
}
