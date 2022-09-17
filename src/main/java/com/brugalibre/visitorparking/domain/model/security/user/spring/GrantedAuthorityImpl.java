package com.brugalibre.visitorparking.domain.model.security.user.spring;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {
   private final String authority;

   public GrantedAuthorityImpl(String authority) {
      this.authority = authority;
   }

   @Override
   public String getAuthority() {
      return authority;
   }
}
