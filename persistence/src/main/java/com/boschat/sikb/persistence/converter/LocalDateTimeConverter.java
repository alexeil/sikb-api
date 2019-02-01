package com.boschat.sikb.persistence.converter;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static com.boschat.sikb.common.utils.DateUtils.getOffsetDateTimeFromTimestamp;
import static com.boschat.sikb.common.utils.DateUtils.getTimestampFromOffsetDateTime;

public class LocalDateTimeConverter implements Converter<Timestamp, OffsetDateTime> {

    @Override
    public OffsetDateTime from(Timestamp t) {
        return getOffsetDateTimeFromTimestamp(t);
    }

    @Override
    public Timestamp to(OffsetDateTime u) {
        return u == null ? null : getTimestampFromOffsetDateTime(u);
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<OffsetDateTime> toType() {
        return OffsetDateTime.class;
    }

}
