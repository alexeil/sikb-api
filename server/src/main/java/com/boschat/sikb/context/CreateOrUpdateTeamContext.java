package com.boschat.sikb.context;

import com.boschat.sikb.model.TeamForCreation;
import com.boschat.sikb.model.TeamForUpdate;
import com.boschat.sikb.model.TeamMemberForCreation;

import java.util.List;

import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_NAME;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateTeamContext {

    private String name;

    private List<TeamMemberForCreation> members;

    private static CreateOrUpdateTeamContext buildCommon(TeamForUpdate team) {
        CreateOrUpdateTeamContext context = new CreateOrUpdateTeamContext();
        context.setName(team.getName());
        context.setMembers(team.getMembers());
        return context;
    }

    public static CreateOrUpdateTeamContext create(TeamForUpdate season) {
        return buildCommon(season);
    }

    public static CreateOrUpdateTeamContext create(TeamForCreation season) {
        checkRequestBodyField(season.getName(), BODY_FIELD_NAME);
        return buildCommon(season);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeamMemberForCreation> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMemberForCreation> members) {
        this.members = members;
    }
}
