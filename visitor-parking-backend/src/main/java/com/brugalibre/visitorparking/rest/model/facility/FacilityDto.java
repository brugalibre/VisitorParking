package com.brugalibre.visitorparking.rest.model.facility;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record FacilityDto(String id, String name) {
   // no-op
}
