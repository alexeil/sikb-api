package com.boschat.sikb.persistence;

import com.boschat.sikb.tables.records.AffiliationRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.boschat.sikb.Tables.AFFILIATION;

public class AffiliationDAO {

    private static final String USERNAME = "postgres";

    private static final String PASSWORD = "postgres";

    private static final String URL = "jdbc:postgresql://localhost:5432/sikb";

    public static Result<AffiliationRecord> getAllAffiliations() {

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            return create.selectFrom(AFFILIATION).fetch();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }
}
