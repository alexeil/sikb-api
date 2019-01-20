package com.boschat.sikb.persistence;

import com.boschat.sikb.tables.daos.UserDao;
import org.jooq.Configuration;

public class UserDAOExtended extends UserDao {

    public UserDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public void truncate() {
        DAOFactory.getInstance().getDslContext().truncate(this.getTable()).cascade().execute();
    }
}
