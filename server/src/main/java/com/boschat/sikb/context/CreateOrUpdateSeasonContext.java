package com.boschat.sikb.context;

import com.boschat.sikb.model.SeasonForUpdate;

import java.time.LocalDate;

public class CreateOrUpdateSeasonContext {

    private String description;

    private LocalDate begin;

    private LocalDate end;

    public static CreateOrUpdateSeasonContext create(SeasonForUpdate season) {
        CreateOrUpdateSeasonContext context = new CreateOrUpdateSeasonContext();
        context.setDescription(season.getDescription());
        context.setBegin(season.getBegin());
        context.setEnd(season.getEnd());
        return context;
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
