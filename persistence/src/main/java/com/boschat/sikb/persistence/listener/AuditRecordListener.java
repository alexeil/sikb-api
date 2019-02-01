package com.boschat.sikb.persistence.listener;

import org.jooq.RecordContext;
import org.jooq.impl.DefaultRecordListener;

import java.sql.Timestamp;
import java.util.stream.Stream;

import static com.boschat.sikb.common.utils.DateUtils.nowAsTimestamp;

public class AuditRecordListener extends DefaultRecordListener {

    private static final String CREATED_DATE_FIELD = "creationDate";

    private static final String UPDATED_DATE_FIELD = "modificationDate";

    @Override
    public void insertStart(RecordContext context) {
        Stream.of(context.record().fields()).forEach(field -> {
            if (field.getName().equals(CREATED_DATE_FIELD)) {
                context.record().set(field.cast(Timestamp.class), nowAsTimestamp());
            } else if (field.getName().equals(UPDATED_DATE_FIELD)) {
                context.record().set(field.cast(Timestamp.class), nowAsTimestamp());
            }
        });
    }

    @Override
    public void updateStart(RecordContext context) {
        Stream.of(context.record().fields()).forEach(field -> {
            if (field.getName().equals(UPDATED_DATE_FIELD)) {
                context.record().set(field.cast(Timestamp.class), nowAsTimestamp());
            }
        });
    }
}
