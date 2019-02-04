module com.boschat.sikb.common {
    requires org.apache.commons.lang3;
    requires org.apache.logging.log4j;
    requires java.sql;
    exports com.boschat.sikb.common.configuration;
    exports com.boschat.sikb.common.exceptions;
    exports com.boschat.sikb.common.utils;
}