package com.boschat.sikb.persistence.listener;

import org.jooq.RecordContext;
import org.jooq.impl.DefaultRecordListener;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static com.boschat.sikb.common.utils.DateUtils.now;

public class AuditRecordListener extends DefaultRecordListener {

    private static final String CREATED_DATE_FIELD = "creationDate";

    private static final String UPDATED_DATE_FIELD = "modificationDate";

    @Override
    public void insertStart(RecordContext context) {
        Stream.of(context.record().fields()).forEach(field -> {
            if (field.getName().equals(CREATED_DATE_FIELD)) {
                context.record().set(field.cast(OffsetDateTime.class), now());
            } else if (field.getName().equals(UPDATED_DATE_FIELD)) {
                context.record().set(field.cast(OffsetDateTime.class), now());
            }
        });
    }

    @Override
    public void updateStart(RecordContext context) {
        Stream.of(context.record().fields()).forEach(field -> {
            if (field.getName().equals(UPDATED_DATE_FIELD)) {
                context.record().set(field.cast(OffsetDateTime.class), now());
            }
        });
    }
}
