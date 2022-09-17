package com.brugalibre.visitorparking.rest.api.parking;

import com.brugalibre.visitorparking.domain.service.parking.add.exception.NoVisitorParkingCardsLeftException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.ParkedCarAlreadyAssignedToVisitorParkingCardException;
import com.brugalibre.visitorparking.domain.service.parking.add.exception.VisitorParkingCardNotValidException;
import com.brugalibre.visitorparking.domain.service.parking.revoke.exception.NoVisitorParkingCardAssignedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@ControllerAdvice
public class ParkServiceControllerExceptionHandler {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult()
              .getAllErrors()
              .forEach(putError(errors));
      return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
   }

   @ExceptionHandler({VisitorParkingCardNotValidException.class, ParkedCarAlreadyAssignedToVisitorParkingCardException.class
           , NoVisitorParkingCardsLeftException.class, NoVisitorParkingCardAssignedException.class})
   protected ResponseEntity<Object> handleOtherExceptions(Exception ex) {
      Map<String, String> errors = Map.of("error", ex.getLocalizedMessage());
      return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
   }

   private static Consumer<ObjectError> putError(Map<String, String> errors) {
      return error -> {
         String fieldName = ((FieldError) error).getField();
         String errorMessage = error.getDefaultMessage();
         errors.put(fieldName, errorMessage);
      };
   }
}
