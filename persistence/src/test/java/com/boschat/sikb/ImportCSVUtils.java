package com.boschat.sikb;

import com.boschat.sikb.persistence.dao.DAOFactory;

import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.executeScript;
import static com.boschat.sikb.PersistenceUtils.loadAffiliations;
import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_DB;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_HOST;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PASSWORD;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PORT;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_USER;

public class ImportCSVUtils {

    private ImportCSVUtils() {

    }

    public static void main(String[] args) throws Exception {
        System.setProperty(POSTGRES_DB.getEnv(), "sikb");
        System.setProperty(POSTGRES_HOST.getEnv(), "localhost");
        System.setProperty(POSTGRES_PORT.getEnv(), "5432");
        System.setProperty(POSTGRES_USER.getEnv(), "postgres");
        System.setProperty(POSTGRES_PASSWORD.getEnv(), "postgres");

        executeScript("persistence/src/main/resources/initDb.sql");
        executeScript("persistence/src/main/resources/initData.sql");
        truncateAndImportClub();
        truncateAndImportAffiliation();
    }

    private static void truncateAndImportClub() throws IOException {
        DAOFactory.getInstance().truncateClub();
        loadClubs("data/prod/insertClub.csv");
    }

    private static void truncateAndImportAffiliation() throws IOException {
        DAOFactory.getInstance().truncateAffiliation();
        loadAffiliations("data/prod/insertAffiliation.csv");
    }
}
