module com.boschat.sikb.persistence {
    exports com.boschat.sikb.tables.pojos;
    exports com.boschat.sikb.persistence.dao;
    exports com.boschat.sikb.tables.daos;
    
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.boschat.sikb.common;
    requires java.compiler;
    requires org.apache.logging.log4j;
    requires org.jooq;
}