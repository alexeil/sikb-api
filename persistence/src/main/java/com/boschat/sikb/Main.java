package com.boschat.sikb;

// For convenience, always static import your generated tables and
// jOOQ functions to decrease verbosity:

import com.boschat.sikb.persistence.AffiliationDAOExtended;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        AffiliationDAOExtended.getAllAffiliations().forEach(affiliationRecord -> System.out.println(affiliationRecord.getAssociationname()));
    }
}
