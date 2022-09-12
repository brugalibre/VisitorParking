package com.brugalibre.visitorparking.domain.model.facility.parking;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ParkedCarTest {

   @Test
   void testGetId() {
      // Given
      String id = "687";
      String carPlateNo = "1234";

      // When
      ParkedCar parkedCar = ParkedCar.builder()
              .carPlateNo(carPlateNo)
              .id(id)
              .build();

      // Then
      assertThat(parkedCar.getId(), is(id));
   }
}