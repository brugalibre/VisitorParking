package com.brugalibre.common.domain.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * The {@link DomainEntity} provides the most basic and necessary fields all entities must have
 * One of these fields is the id of the entity. The {@link DomainEntity} provides an auto generated UUID
 *
 * @author DStalder
 */
@MappedSuperclass
public abstract class DomainEntity implements IEntity<String> {

   @Id
   @Column(name = "id", updatable = false, nullable = false)
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   protected String id;

   public DomainEntity(UUID id) {
      this.id = id == null ? UUID.randomUUID().toString() : id.toString();
   }

   public String getId() {
      return id;
   }

   // for mapstruct
   public void setId(String id) {
      this.id = id;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + id.hashCode();
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      DomainEntity other = (DomainEntity) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      return true;
   }
}