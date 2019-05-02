package com.boschat.sikb.persistence.dao;

import com.boschat.sikb.tables.daos.AffiliationDao;
import com.boschat.sikb.tables.pojos.Affiliation;
import com.boschat.sikb.tables.records.AffiliationRecord;
import org.jooq.Configuration;
import org.jooq.Result;

import java.util.Map;

import static com.boschat.sikb.Tables.AFFILIATION;
import static org.jooq.impl.DSL.using;

public class AffiliationDAOExtended extends AffiliationDao {

    public AffiliationDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public Affiliation fetchByIdClubIdSeason(Integer clubId, String season) {
        AffiliationRecord record = using(this.configuration())
            .selectFrom(this.getTable())
            .where(AFFILIATION.CLUBID.equal(clubId))
            .and(AFFILIATION.SEASON.equal(season))
            .orderBy(AFFILIATION.SEASON.desc())
            .fetchOne();

        return record == null ? null : mapper().map(record);
    }

    public Map<String, Affiliation> fetchByClubIdMappedBySeason(Integer clubId) {
        Result<AffiliationRecord> record = using(this.configuration())
            .selectFrom(this.getTable())
            .where(AFFILIATION.CLUBID.equal(clubId))
            .orderBy(AFFILIATION.SEASON.desc())
            .fetch();

        return record.intoMap(AFFILIATION.SEASON, this.mapper());
    }
}
