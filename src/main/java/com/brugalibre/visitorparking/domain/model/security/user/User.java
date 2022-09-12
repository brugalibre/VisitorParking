package com.brugalibre.visitorparking.domain.model.security.user;

import com.brugalibre.visitorparking.domain.model.user.Role;
import com.brugalibre.visitorparking.domain.model.security.user.spring.GrantedAuthorityImpl;
import com.brugalibre.visitorparking.persistence.user.UserEntity;

import java.util.List;

public class User extends org.springframework.security.core.userdetails.User {

   private String id;

   public User() {
      super("null", "null", List.of());
   }

   public User(UserEntity userEntity) {
      super(userEntity.getUsername(), userEntity.getPassword(), mapRoles2GrantedAuthorities(userEntity.getRoles()));
      this.id = userEntity.getId();
   }

   public User(String username, String password, List<Role> roles) {
      super(username, password, mapRoles2GrantedAuthorities(roles));
   }

   public String getId() {
      return id;
   }

   public static List<GrantedAuthorityImpl> mapRoles2GrantedAuthorities(List<Role> roles) {
      return roles.stream()
              .map(Enum::name)
              .map(GrantedAuthorityImpl::new).toList();
   }

   /**
    * Returns <code>true</code> if this user has the same username as the given one. Otherwise returns <code>false</code>
    *
    * @param user the {@link User} to check
    * @return <code>true</code> if this user has the same username as the given one. Otherwise returns <code>false</code>
    */
   public boolean isSame(User user) {
      return this.getUsername().equals(user.getUsername());
   }
}
