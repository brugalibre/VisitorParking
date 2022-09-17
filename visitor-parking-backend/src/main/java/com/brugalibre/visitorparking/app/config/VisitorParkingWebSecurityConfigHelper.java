package com.brugalibre.visitorparking.app.config;

import com.brugalibre.common.security.auth.config.WebSecurityConfigHelper;
import com.brugalibre.persistence.user.Role;
import org.springframework.stereotype.Service;

@Service
public class VisitorParkingWebSecurityConfigHelper implements WebSecurityConfigHelper {
   @Override
   public String[] getRequestMatcherForRole(String role) {
      if (Role.ADMIN.name().equals(role)) {
         return new String[]{"/api/v1/visitor-parking-management/admin/**"};
      } else if (Role.USER.name().equals(role)) {
         return new String[]{"/api/v1/visitor-parking-management/**"};
      }
      return new String[]{};
   }

   @Override
   public String getLoginProcessingUrl() {
      return "/besucher-parking-verwaltung";
   }
}
