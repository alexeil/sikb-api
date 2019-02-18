package com.boschat.sikb.persistence.dao;

import com.boschat.sikb.tables.daos.LicenceDao;
import com.boschat.sikb.tables.pojos.Licence;
import org.jooq.Configuration;
import org.jooq.impl.DSL;

import java.util.List;

import static com.boschat.sikb.tables.Licence.LICENCE;

public class LicenceDAOExtended extends LicenceDao {

    public LicenceDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public List<Licence> findAllByPersonIdClubIdSeason(Integer personId, Integer clubId, String season) {
        return DSL.using(this.configuration())
                  .selectFrom(this.getTable())
                  .where(LICENCE.PERSONID.eq(personId))
                  .and(LICENCE.CLUBID.eq(clubId))
                  .and(LICENCE.SEASON.eq(season))
                  .fetch()
                  .map(this.mapper());

    }
}
