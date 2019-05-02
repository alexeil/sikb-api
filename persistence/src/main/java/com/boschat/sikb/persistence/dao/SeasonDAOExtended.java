package com.boschat.sikb.persistence.dao;

import com.boschat.sikb.tables.daos.SeasonDao;
import com.boschat.sikb.tables.pojos.Season;
import org.jooq.Configuration;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Map;

import static com.boschat.sikb.Tables.SEASON;

public class SeasonDAOExtended extends SeasonDao {

    public SeasonDAOExtended(Configuration configuration) {
        super(configuration);
    }

    @Override
    public List<Season> findAll() {
        return DSL.using(this.configuration())
                  .selectFrom(this.getTable()).orderBy(SEASON.BEGIN.desc())
                  .fetch()
                  .map(this.mapper());
    }

    public Map<String, Season> findAllMappedById() {
        return DSL.using(this.configuration())
                  .selectFrom(this.getTable()).orderBy(SEASON.BEGIN.desc())
                  .fetch()
                  .intoMap(SEASON.ID, this.mapper());
    }
}
