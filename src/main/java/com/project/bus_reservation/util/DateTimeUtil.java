package com.project.bus_reservation.util;

import com.google.cloud.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtil {

    public static Timestamp toTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Timestamp.of(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static Timestamp toTimestamp(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Timestamp.of(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
