package com.brugalibre.visitorparking.rest.model.resident;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ResidentDto(String id, String name, String facilityId, List<VisitorParkingCardDto> visitorParkingCards) {
   // no-op
}
