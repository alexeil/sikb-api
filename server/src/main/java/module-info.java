module com.boschat.sikb.server {

    requires org.apache.logging.log4j;
    requires org.jooq;
    requires com.fasterxml.jackson.core;
    requires jackson.annotations;
    requires migbase64;
    requires javax.servlet.api;
    requires javax.annotation.api;
    requires validation.api;
    requires java.ws.rs;
    requires com.boschat.sikb.common;
    requires org.apache.commons.collections4;
    requires org.apache.commons.lang3;
    requires org.bouncycastle.provider;
    requires com.fasterxml.jackson.databind;
    requires mail;
    requires com.boschat.sikb.persistence;
    requires com.fasterxml.jackson.jaxrs.json;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jersey.media.multipart;
    requires swagger.annotations;
}