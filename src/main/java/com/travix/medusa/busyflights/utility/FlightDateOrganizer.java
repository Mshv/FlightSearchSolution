package com.travix.medusa.busyflights.utility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class FlightDateOrganizer {

  public static String dateConventor(int year, int month, int dayOfMonth) {
    ZonedDateTime zonedDateTime = LocalDate
        .of(year, month, dayOfMonth)
        .atTime(0, 0, 0)
        .atZone(ZoneId.of("Europe/Amsterdam"));
    return DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime);
  }
}
