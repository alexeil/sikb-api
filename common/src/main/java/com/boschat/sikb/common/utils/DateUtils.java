package com.boschat.sikb.common.utils;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

public class DateUtils {

    private static final DateTimeFormatter FRENCH_LOCAL_DATE;

    static {
        FRENCH_LOCAL_DATE = new DateTimeFormatterBuilder()
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral('/')
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('/')
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .toFormatter();
    }

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

    public static LocalDate parseLocalDate(String localDateAsString) {
        return LocalDate.parse(localDateAsString);
    }

    public static String formatFrenchLocalDate(LocalDate localDate) {
        return localDate.format(FRENCH_LOCAL_DATE);
    }
}
