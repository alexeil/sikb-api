package com.boschat.sikb.servlet;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.model.DocumentType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.Level.ERROR;

public class DocumentServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(DocumentServlet.class);

    private static void logAndBuildResponse(HttpServletResponse response, Level level, Short codeHttp,
        Throwable e) throws IOException {
        LOGGER.log(level, e.getMessage(), e);
        response.getOutputStream().println(e.getMessage());
        response.setStatus(codeHttp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String type = request.getParameter("type");
        String id = request.getParameter("id");

        try {
            DocumentType.fromValue(type).writeDocument(id, response.getOutputStream());
        } catch (FunctionalException e) {
            logAndBuildResponse(response, e.getErrorCode().getLevel(), e.getErrorCode().getCodeHttp(), e);
        } catch (Throwable e) {
            logAndBuildResponse(response, ERROR, (short) 500, e);
        } finally {
            LOGGER.info("%s with id %s generated !", type, id);
        }
    }
}
