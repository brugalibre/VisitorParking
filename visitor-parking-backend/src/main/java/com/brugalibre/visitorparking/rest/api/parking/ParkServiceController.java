package com.brugalibre.visitorparking.rest.api.parking;

import com.brugalibre.visitorparking.domain.service.parking.add.ParkCarService;
import com.brugalibre.visitorparking.domain.service.parking.revoke.RevokeParkedCarService;
import com.brugalibre.visitorparking.rest.model.parking.ParkCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/visitor-parking-management/parkservice")
@RestController
public class ParkServiceController {

   private final ParkCarService parkCarService;
   private final RevokeParkedCarService revokeParkedCarService;

   @Autowired
   public ParkServiceController(ParkCarService parkCarService, RevokeParkedCarService revokeParkedCarService) {
      this.parkCarService = parkCarService;
      this.revokeParkedCarService = revokeParkedCarService;
   }

   @RequestMapping(value = "/parkCar/{residentId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
   public int parkCar(@PathVariable String residentId, @RequestBody ParkCarRequest parkCarRequest) {
      parkCarService.assignParkedCar4PlateNo(residentId, parkCarRequest.car2ParkPlateNo());
      return HttpStatus.OK.value();
   }

   @GetMapping(path = "/isParkedCarAssigned/{parkingLotId}/{carPlateNo}")
   public boolean isParkedCarAssignedByPlateNo(@PathVariable String parkingLotId, @PathVariable String carPlateNo) {
      return parkCarService.isParkedCarAssignedByPlateNo(parkingLotId, carPlateNo);
   }

   @DeleteMapping(path = "/parkedCar/{residentId}/{carPlateNo}")
   public int revokeParkedCar(@PathVariable String residentId, @PathVariable String carPlateNo) {
      revokeParkedCarService.revokeParkedCar4PlateNo(residentId, carPlateNo);
      return HttpStatus.OK.value();
   }
}
