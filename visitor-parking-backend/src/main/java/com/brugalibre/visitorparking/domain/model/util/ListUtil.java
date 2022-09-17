package com.brugalibre.visitorparking.domain.model.util;

import com.brugalibre.common.domain.model.DomainModel;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class ListUtil {
   private ListUtil() {
      //private
   }

   /**
    * Removes an existing element if already exist. This method is used for elements, for which 'equals' won't work.
    * It would return false although the two elements represents still the sa
    * has changed, we have to find an existing DomainModel regardless of it's visitor-parking-cards.
    */
   public static <T extends DomainModel> void replaceExistingDomainModelIfPresent(T domainModel, List<T> newDomainModels) {
      Optional<T> existingResidentOpt = findExistingResidentOptional(domainModel, newDomainModels);
      existingResidentOpt.ifPresent(newDomainModels::remove);
      newDomainModels.add(domainModel);
   }

   private static <T extends DomainModel> Optional<T> findExistingResidentOptional(T domainModel, List<T> newDomainModels) {
      return newDomainModels.stream()
              .filter(existingResident -> nonNull(existingResident.getId()))
              .filter(existingResident -> existingResident.getId().equals(domainModel.getId()))
              .findFirst();
   }
}
