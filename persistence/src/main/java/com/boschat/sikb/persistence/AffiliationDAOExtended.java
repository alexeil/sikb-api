package com.boschat.sikb.persistence;

import com.boschat.sikb.tables.daos.AffiliationDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class AffiliationDAOExtended extends AffiliationDao {

    public AffiliationDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public void truncate() {
        DSLContext create = DSL.using(DAOFactory.getInstance().getConnection(), SQLDialect.POSTGRES);
        create.truncate(this.getTable());
    }
}
