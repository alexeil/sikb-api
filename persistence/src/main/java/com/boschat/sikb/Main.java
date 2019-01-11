package com.boschat.sikb;

// For convenience, always static import your generated tables and
// jOOQ functions to decrease verbosity:

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.boschat.sikb.Tables.AFFILIATION;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String userName = "postgres";
        String password = "postgres";
        String url = "jdbc:postgresql://localhost:5432/sikb";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(AFFILIATION).fetch();

            for (Record r : result) {
                Integer id = r.getValue(AFFILIATION.ID);
                String name = r.getValue(AFFILIATION.ASSOCIATIONNAME);
                String email = r.getValue(AFFILIATION.EMAIL);

                System.out.println("ID: " + id + " name: " + name + " email: " + email);
            }
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
