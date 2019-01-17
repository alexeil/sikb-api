package com.boschat.sikb.persistence;

import com.boschat.sikb.tables.daos.ClubDao;
import org.jooq.Configuration;

public class ClubDAOExtended extends ClubDao {

    public ClubDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public void truncate() {
        DAOFactory.getInstance().getDslContext().truncate(this.getTable()).execute();
    }
}
