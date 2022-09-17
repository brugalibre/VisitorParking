package com.brugalibre.visitorparking.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
   private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
   private static final String LOG_ERROR_MSG = "Exception in Thread %s. Application is going to be shutdown";

   public GlobalExceptionHandler() {
   }

   public void uncaughtException(Thread t, Throwable e) {
      LOG.error(LOG_ERROR_MSG.formatted(t), e);
      System.exit(-1);
   }
}
