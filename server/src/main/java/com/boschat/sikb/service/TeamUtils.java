package com.boschat.sikb.service;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.CreateOrUpdateTeamContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.model.TeamMember;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Team;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.TEAM_NOT_FOUND;
import static com.boschat.sikb.service.ClubUtils.checkClubExists;
import static com.boschat.sikb.service.SeasonUtils.checkSeasonExists;
import static com.boschat.sikb.utils.JsonUtils.jsonNodeToTeamMembers;
import static com.boschat.sikb.utils.JsonUtils.teamEnrollmentToJsonNode;

public class TeamUtils {

    private TeamUtils() {

    }

    public static Team updateTeam() {
        return saveTeam(true);
    }

    public static Team createTeam() {
        return saveTeam(false);
    }

    private static Team saveTeam(boolean isModification) {
        CreateOrUpdateTeamContext context = MyThreadLocal.get().getCreateOrUpdateTeamContext();
        Team teamBean;
        if (isModification) {
            teamBean = getTeam();
        } else {
            teamBean = new Team();

            Integer clubId = MyThreadLocal.get().getClubId();
            String seasonId = MyThreadLocal.get().getSeasonId();

            checkSeasonExists();
            teamBean.setSeason(seasonId);

            checkClubExists();
            teamBean.setClubid(clubId);
        }

        if (context.getName() != null) {
            teamBean.setName(context.getName());
        }
        if (context.getMembers() != null) {
            teamBean.setTeammembers(teamEnrollmentToJsonNode(context.getMembers()));
        }

        if (isModification) {
            DAOFactory.getInstance().getTeamDAO().update(teamBean);
        } else {
            DAOFactory.getInstance().getTeamDAO().insert(teamBean);
        }

        return teamBean;
    }

    public static Team getTeam() {
        Integer licenceId = MyThreadLocal.get().getTeamId();
        Team licence = DAOFactory.getInstance().getTeamDAO().fetchOneById(licenceId);

        if (licence == null) {
            throw new FunctionalException(TEAM_NOT_FOUND, licenceId);
        }
        return licence;
    }

    public static void deleteTeam() {
        checkSeasonExists();
        checkClubExists();
        DAOFactory.getInstance().getTeamDAO().deleteById(MyThreadLocal.get().getTeamId());
    }

    public static List<Team> findTeams() {
        String seasonId = MyThreadLocal.get().getSeasonId();
        Integer clubId = MyThreadLocal.get().getClubId();

        checkSeasonExists();
        checkClubExists();

        return DAOFactory.getInstance().getTeamDAO().findAllByClubIdSeason(clubId, seasonId);
    }

    public static List<TeamMember> findTeamMembers() {
        return jsonNodeToTeamMembers(getTeam().getTeammembers());
    }

}
