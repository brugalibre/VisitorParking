package com.brugalibre.visitorparking.persistence.user.mapper;

import com.brugalibre.visitorparking.domain.model.security.user.User;
import com.brugalibre.visitorparking.domain.model.user.Role;
import com.brugalibre.visitorparking.persistence.user.UserEntity;
import org.mapstruct.*;
import org.springframework.security.core.GrantedAuthority;

@Mapper
public interface UserEntityMapper {

   @Mapping(target = "roles", ignore = true)
   UserEntity map2UserEntity(User user);

   @AfterMapping
   default void mapAuthorities2Roles(@MappingTarget UserEntity userEntity, User user) {
      userEntity.setRoles(user.getAuthorities()
              .stream()
              .map(GrantedAuthority::getAuthority)
              .map(Role::valueOf)
              .toList());
   }

   @Mapping(target = "authorities", ignore = true)
   User map2User(UserEntity userEntity);

   /**
    * We have to do de mapping manually, since the {@link org.springframework.security.core.userdetails.User} is immutable,
    * but we have to map the authority-values
    */
   @BeforeMapping
   default User toUser(UserEntity userEntity) {
      return new User(userEntity);
   }
}