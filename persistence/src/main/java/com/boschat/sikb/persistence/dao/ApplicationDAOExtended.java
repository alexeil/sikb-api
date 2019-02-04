package com.boschat.sikb.persistence.dao;

import com.boschat.sikb.tables.daos.ApplicationDao;
import com.boschat.sikb.tables.pojos.Application;
import com.boschat.sikb.tables.records.ApplicationRecord;
import org.jooq.Configuration;

import static com.boschat.sikb.Tables.APPLICATION;
import static org.jooq.impl.DSL.using;

public class ApplicationDAOExtended extends ApplicationDao {

    public ApplicationDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public Application fetchByLoginPassword(String login, String password) {
        ApplicationRecord record = using(this.configuration())
            .selectFrom(this.getTable())
            .where(APPLICATION.LOGIN.equal(login))
            .and(APPLICATION.PASSWORD.equal(password))
            .fetchOne();

        return record == null ? null : mapper().map(record);
    }

}
