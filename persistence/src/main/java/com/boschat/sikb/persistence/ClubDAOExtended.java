package com.boschat.sikb.persistence;

import com.boschat.sikb.tables.daos.ClubDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class ClubDAOExtended extends ClubDao {

    public ClubDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public void truncate() {
        DSLContext create = DSL.using(DAOFactory.getInstance().getConnection(), SQLDialect.POSTGRES);
        create.truncate(this.getTable());
    }
}
