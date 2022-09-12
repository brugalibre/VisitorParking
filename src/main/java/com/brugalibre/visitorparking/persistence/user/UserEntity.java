package com.brugalibre.visitorparking.persistence.user;

import com.brugalibre.visitorparking.domain.model.user.Role;
import com.brugalibre.common.domain.persistence.DomainEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity extends DomainEntity {

   @NotBlank
   private String username;

   @NotBlank
   private String password;

   @ElementCollection
   @Enumerated(value = EnumType.STRING)
   @LazyCollection(LazyCollectionOption.FALSE)
   private List<Role> roles;

   public UserEntity() {
      super(null);
      this.roles = new ArrayList<>();
   }

   public UserEntity(String username, String password, List<Role> roles) {
      super(null);
      this.username = username;
      this.password = password;
      this.roles = new ArrayList<>(roles);
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = new ArrayList<>(roles);
   }
}