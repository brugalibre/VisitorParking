package com.brugalibre.visitorparking.domain.model.util;

import com.brugalibre.visitorparking.domain.model.resident.Resident;
import com.brugalibre.visitorparking.domain.model.resident.VisitorParkingCard;
import com.brugalibre.visitorparking.domain.model.security.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ListUtilTest {

   @Test
   void replaceExistingDomainModelSinceItsPresent() {
      // Given
      List<Resident> residents = new ArrayList<>();
      String residentId1 = "1234";
      String residentId2 = "4321";
      Resident existingResidentToBeReplaced = Resident.builder()
              .id(residentId1)
              .user(new User("123", "32", List.of()))
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingCardNr(residentId1)
                      .isAvailable(true)
                      .build()))
              .build();
      residents.add(existingResidentToBeReplaced);

      Resident newResidentToAdd = Resident.builder()
              .id(residentId2)
              .user(new User("321", "32", List.of()))
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingCardNr(residentId1)
                      .isAvailable(false)
                      .build()))
              .build();

      Resident changedExistingResident = Resident.builder()
              .id(residentId1)
              .user(new User("123", "32", List.of()))
              .visitorParkingCards(List.of(VisitorParkingCard.builder()
                      .parkingCardNr(residentId1)
                      .isAvailable(false)// no change only the visitor parking card
                      .build()))
              .build();

      // When
      ListUtil.replaceExistingDomainModelIfPresent(changedExistingResident, residents);
      ListUtil.replaceExistingDomainModelIfPresent(newResidentToAdd, residents);

      // Then
      assertThat(residents.contains(existingResidentToBeReplaced), is(false));
      assertThat(residents.contains(changedExistingResident), is(true));
      assertThat(residents.contains(newResidentToAdd), is(true));
   }
}