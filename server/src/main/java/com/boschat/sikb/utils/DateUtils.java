package com.boschat.sikb.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateUtils {

    private static Clock clock = Clock.systemDefaultZone();

    private static ZoneOffset currentZoneOffSet = OffsetDateTime.now().getOffset();

    private DateUtils() {

    }

    public static OffsetDateTime now() {
        return OffsetDateTime.now(getClock());
    }

    /**
     * use ONLY in test
     *
     * @param date
     */
    public static void useFixedClockAt(OffsetDateTime date) {
        clock = Clock.fixed(date.toInstant(), currentZoneOffSet);
    }

    private static Clock getClock() {
        return clock;
    }

    public static ZoneOffset getCurrentZoneOffSet() {
        return currentZoneOffSet;
    }

    public static Timestamp getTimestampFromOffsetDateTime(OffsetDateTime datetime) {
        if (datetime != null) {
            return Timestamp.valueOf(datetime.toLocalDateTime());
        }
        return null;
    }

    public static OffsetDateTime getOffsetDateTimeFromTimestamp(Timestamp datetime) {
        if (datetime != null) {
            return OffsetDateTime.of(datetime.toLocalDateTime(), currentZoneOffSet);
        }
        return null;
    }

    public static Date getDateFromLocalDate(LocalDate date) {
        if (date != null) {
            return Date.valueOf(date);
        }
        return null;
    }

}
