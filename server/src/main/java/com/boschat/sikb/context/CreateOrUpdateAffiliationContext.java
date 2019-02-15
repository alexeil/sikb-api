package com.boschat.sikb.context;

import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.Board;
import com.boschat.sikb.model.Sex;

import java.time.LocalDate;

import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_ADDRESS;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_BOARD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_CITY;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_ELECTED_DATE;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_EMAIL;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_MEMBERS_NUMBER;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_POSTAL_CODE;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PREFECTURE_CITY;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PREFECTURE_NUMBER;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PRESIDENT;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PRESIDENT_SEX;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SECRETARY;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SECRETARY_SEX;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SIRET_NUMBER;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_TREASURER;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_TREASURER_SEX;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateAffiliationContext {

    private String prefectureNumber;

    private String siretNumber;

    private String address;

    private String postalCode;

    private String city;

    private String phoneNumber;

    private String email;

    private String webSite;

    private String president;

    private Sex presidentSex;

    private String secretary;

    private Sex secretarySex;

    private String treasurer;

    private Sex treasurerSex;

    private Integer membersNumber;

    private LocalDate electedDate;

    private String prefectureCity;

    private static CreateOrUpdateAffiliationContext buildContext(AffiliationForUpdate affiliationForCreation) {
        CreateOrUpdateAffiliationContext createOrUpdateContext = new CreateOrUpdateAffiliationContext();
        createOrUpdateContext.setPrefectureNumber(affiliationForCreation.getPrefectureNumber());
        createOrUpdateContext.setPrefectureCity(affiliationForCreation.getPrefectureCity());
        createOrUpdateContext.setSiretNumber(affiliationForCreation.getSiretNumber());
        createOrUpdateContext.setAddress(affiliationForCreation.getAddress());
        createOrUpdateContext.setPostalCode(affiliationForCreation.getPostalCode());
        createOrUpdateContext.setCity(affiliationForCreation.getCity());
        createOrUpdateContext.setPhoneNumber(affiliationForCreation.getPhoneNumber());
        createOrUpdateContext.setEmail(affiliationForCreation.getEmail());
        createOrUpdateContext.setWebSite(affiliationForCreation.getWebSite());

        Board board = affiliationForCreation.getBoard();
        if (board != null) {
            createOrUpdateContext.setPresident(board.getPresident());
            createOrUpdateContext.setPresidentSex(board.getPresidentSex());
            createOrUpdateContext.setSecretary(board.getSecretary());
            createOrUpdateContext.setSecretarySex(board.getSecretarySex());
            createOrUpdateContext.setTreasurer(board.getTreasurer());
            createOrUpdateContext.setTreasurerSex(board.getTreasurerSex());
            createOrUpdateContext.setMembersNumber(board.getMembersNumber());
            createOrUpdateContext.setElectedDate(board.getElectedDate());
        }
        return createOrUpdateContext;
    }

    public static CreateOrUpdateAffiliationContext create(AffiliationForUpdate affiliationForUpdate) {
        return buildContext(affiliationForUpdate);
    }

    public static CreateOrUpdateAffiliationContext create(AffiliationForCreation affiliationForCreation) {
        checkRequestBodyField(affiliationForCreation.getPrefectureNumber(), BODY_FIELD_PREFECTURE_NUMBER);
        checkRequestBodyField(affiliationForCreation.getPrefectureCity(), BODY_FIELD_PREFECTURE_CITY);
        checkRequestBodyField(affiliationForCreation.getSiretNumber(), BODY_FIELD_SIRET_NUMBER);
        checkRequestBodyField(affiliationForCreation.getAddress(), BODY_FIELD_ADDRESS);
        checkRequestBodyField(affiliationForCreation.getPostalCode(), BODY_FIELD_POSTAL_CODE);
        checkRequestBodyField(affiliationForCreation.getCity(), BODY_FIELD_CITY);
        checkRequestBodyField(affiliationForCreation.getEmail(), BODY_FIELD_EMAIL);
        checkRequestBodyField(affiliationForCreation.getBoard(), BODY_FIELD_BOARD);

        checkRequestBodyField(affiliationForCreation.getBoard().getPresident(), BODY_FIELD_PRESIDENT);
        checkRequestBodyField(affiliationForCreation.getBoard().getPresidentSex(), BODY_FIELD_PRESIDENT_SEX);
        checkRequestBodyField(affiliationForCreation.getBoard().getSecretary(), BODY_FIELD_SECRETARY);
        checkRequestBodyField(affiliationForCreation.getBoard().getSecretarySex(), BODY_FIELD_SECRETARY_SEX);
        checkRequestBodyField(affiliationForCreation.getBoard().getTreasurer(), BODY_FIELD_TREASURER);
        checkRequestBodyField(affiliationForCreation.getBoard().getTreasurerSex(), BODY_FIELD_TREASURER_SEX);
        checkRequestBodyField(affiliationForCreation.getBoard().getMembersNumber(), BODY_FIELD_MEMBERS_NUMBER);
        checkRequestBodyField(affiliationForCreation.getBoard().getElectedDate(), BODY_FIELD_ELECTED_DATE);

        return buildContext(affiliationForCreation);
    }

    public String getPrefectureNumber() {
        return prefectureNumber;
    }

    public void setPrefectureNumber(String prefectureNumber) {
        this.prefectureNumber = prefectureNumber;
    }

    public String getSiretNumber() {
        return siretNumber;
    }

    public void setSiretNumber(String siretNumber) {
        this.siretNumber = siretNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public Sex getPresidentSex() {
        return presidentSex;
    }

    public void setPresidentSex(Sex presidentSex) {
        this.presidentSex = presidentSex;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    public Sex getSecretarySex() {
        return secretarySex;
    }

    public void setSecretarySex(Sex secretarySex) {
        this.secretarySex = secretarySex;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }

    public Sex getTreasurerSex() {
        return treasurerSex;
    }

    public void setTreasurerSex(Sex treasurerSex) {
        this.treasurerSex = treasurerSex;
    }

    public Integer getMembersNumber() {
        return membersNumber;
    }

    public void setMembersNumber(Integer membersNumber) {
        this.membersNumber = membersNumber;
    }

    public LocalDate getElectedDate() {
        return electedDate;
    }

    public void setElectedDate(LocalDate electedDate) {
        this.electedDate = electedDate;
    }

    public String getPrefectureCity() {
        return prefectureCity;
    }

    public void setPrefectureCity(String prefectureCity) {
        this.prefectureCity = prefectureCity;
    }
}
