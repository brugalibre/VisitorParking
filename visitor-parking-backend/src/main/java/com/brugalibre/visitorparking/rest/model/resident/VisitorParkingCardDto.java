package com.brugalibre.visitorparking.rest.model.resident;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record VisitorParkingCardDto(String id, String parkingCardNr, String parkingLotId, String parkedCarPlateNo,
                                    boolean isAvailable, LocalDateTime assignedSince) {
}
