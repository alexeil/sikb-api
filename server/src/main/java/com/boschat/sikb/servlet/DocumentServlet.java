package com.boschat.sikb.servlet;

import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Person;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.boschat.sikb.model.DocumentType.MEDICAL_CERTIFICATE_TYPE;

public class DocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if (MEDICAL_CERTIFICATE_TYPE.getKey().equals(type)) {
            Person person = DAOFactory.getInstance().getPersonDAO().fetchOneByMedicalcertificatekey(id);
            byte[] data = person.getMedicalcertificatedata();
            String contentType = person.getMedicalcertificatecontenttype();

            response.setContentType(contentType);
            response.getOutputStream().write(data);
        }
    }
}
