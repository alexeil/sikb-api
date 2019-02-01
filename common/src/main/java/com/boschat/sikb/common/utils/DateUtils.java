package com.boschat.sikb.common.utils;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateUtils {

    private static Clock clock = Clock.systemDefaultZone();

    private static ZoneOffset currentZoneOffSet = OffsetDateTime.now().getOffset();

    private DateUtils() {

    }

    /**
     * use ONLY in test
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

    public static OffsetDateTime now() {
        return OffsetDateTime.now(getClock());
    }

    public static OffsetDateTime nowPlusDays(Integer days) {
        return now().plusDays(days);
    }

    public static Timestamp getTimestampFromOffsetDateTime(OffsetDateTime datetime) {
        return datetime == null ? null : Timestamp.valueOf(datetime.toLocalDateTime());
    }

    public static OffsetDateTime getOffsetDateTimeFromTimestamp(Timestamp datetime) {
        return datetime == null ? null : OffsetDateTime.of(datetime.toLocalDateTime(), getCurrentZoneOffSet());
    }

}
