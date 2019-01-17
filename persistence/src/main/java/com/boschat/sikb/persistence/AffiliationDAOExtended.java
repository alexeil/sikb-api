package com.boschat.sikb.persistence;

import com.boschat.sikb.tables.daos.AffiliationDao;
import org.jooq.Configuration;

public class AffiliationDAOExtended extends AffiliationDao {

    public AffiliationDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public void truncate() {
        DAOFactory.getInstance().getDslContext().truncate(this.getTable()).execute();
    }
}
