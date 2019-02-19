package com.boschat.sikb.persistence.dao;

import com.boschat.sikb.tables.daos.TeamDao;
import com.boschat.sikb.tables.pojos.Team;
import org.jooq.Configuration;
import org.jooq.impl.DSL;

import java.util.List;

import static com.boschat.sikb.tables.Team.TEAM;

public class TeamDAOExtended extends TeamDao {

    public TeamDAOExtended(Configuration configuration) {
        super(configuration);
    }

    public List<Team> findAllByClubIdSeason(Integer clubId, String season) {
        return DSL.using(this.configuration())
                  .selectFrom(this.getTable())
                  .where(TEAM.CLUBID.eq(clubId))
                  .and(TEAM.SEASON.eq(season))
                  .fetch()
                  .map(this.mapper());

    }
}
