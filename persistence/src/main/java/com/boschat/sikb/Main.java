package com.boschat.sikb;

// For convenience, always static import your generated tables and
// jOOQ functions to decrease verbosity:

import com.boschat.sikb.persistence.AffiliationDAO;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        AffiliationDAO.getAllAffiliations().forEach(affiliationRecord -> System.out.println(affiliationRecord.getAssociationname()));
    }
}
