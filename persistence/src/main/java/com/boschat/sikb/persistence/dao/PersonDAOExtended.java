package com.boschat.sikb.persistence.dao;

import com.boschat.sikb.tables.daos.PersonDao;
import org.jooq.Configuration;

public class PersonDAOExtended extends PersonDao {

    public PersonDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public void truncate() {
        DAOFactory.getInstance().getDslContext().truncate(this.getTable()).cascade().execute();
    }
}
