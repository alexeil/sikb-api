package com.boschat.sikb.servlet;

import com.boschat.sikb.model.DocumentType;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String type = request.getParameter("type");
        String id = request.getParameter("id");

        DocumentType.fromValue(type).writeDocument(id, response.getOutputStream());
    }
}
