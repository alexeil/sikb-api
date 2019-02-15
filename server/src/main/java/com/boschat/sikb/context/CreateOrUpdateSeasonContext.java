package com.boschat.sikb.context;

import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;

import java.time.LocalDate;

import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SEASON_BEGIN;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SEASON_DESCRIPTION;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SEASON_END;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateSeasonContext {

    private String description;

    private LocalDate begin;

    private LocalDate end;

    public static CreateOrUpdateSeasonContext buildCommon(SeasonForUpdate season) {
        CreateOrUpdateSeasonContext context = new CreateOrUpdateSeasonContext();
        context.setDescription(season.getDescription());
        context.setBegin(season.getBegin());
        context.setEnd(season.getEnd());
        return context;
    }

    public static CreateOrUpdateSeasonContext create(SeasonForUpdate season) {
        return buildCommon(season);
    }

    public static CreateOrUpdateSeasonContext create(SeasonForCreation season) {
        checkRequestBodyField(season.getDescription(), BODY_FIELD_SEASON_DESCRIPTION);
        checkRequestBodyField(season.getBegin(), BODY_FIELD_SEASON_BEGIN);
        checkRequestBodyField(season.getEnd(), BODY_FIELD_SEASON_END);

        return buildCommon(season);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
