package com.boschat.sikb.servlet;

import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Club;
import com.boschat.sikb.tables.pojos.Licence;
import com.boschat.sikb.tables.pojos.Person;
import com.boschat.sikb.tables.pojos.Season;
import com.boschat.sikb.utils.PDFGeneratorUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.boschat.sikb.model.DocumentType.LICENCE_TYPE;
import static com.boschat.sikb.model.DocumentType.MEDICAL_CERTIFICATE_TYPE;

public class DocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String type = request.getParameter("type");
        String id = request.getParameter("id");

        if (MEDICAL_CERTIFICATE_TYPE.getKey().equals(type)) {
            Person person = DAOFactory.getInstance().getPersonDAO().fetchOneByMedicalcertificatekey(id);
            byte[] data = person.getMedicalcertificatedata();
            String contentType = person.getMedicalcertificatecontenttype();

            response.setContentType(contentType);
            response.getOutputStream().write(data);

        } else if (LICENCE_TYPE.getKey().equals(type)) {
            Licence licence = DAOFactory.getInstance().getLicenceDAO().fetchOneByNumber(id);

            Person person = DAOFactory.getInstance().getPersonDAO().fetchOneById(licence.getPersonid());
            Club club = DAOFactory.getInstance().getClubDAO().fetchOneById(licence.getClubid());
            Season season = DAOFactory.getInstance().getSeasonDAO().fetchOneById(licence.getSeason());

            PDFGeneratorUtils.getInstance().generateLicencePdf(person, club, season, licence, response.getOutputStream());
        }
    }
}
