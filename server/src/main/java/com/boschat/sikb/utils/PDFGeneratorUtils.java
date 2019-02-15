package com.boschat.sikb.utils;

import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.LicenceType;
import com.boschat.sikb.tables.pojos.Club;
import com.boschat.sikb.tables.pojos.Licence;
import com.boschat.sikb.tables.pojos.Person;
import com.boschat.sikb.tables.pojos.Season;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_DIRECTORY;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_BACKGROUND;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_COLORS_BY_LICENCE_TYPE;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_DEFAULT_PHOTO;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_INFORMATION_BACKGROUND;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_LOGO;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_NAME;
import static com.boschat.sikb.common.configuration.ResponseCode.EXPORT_PDF_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.JASPER_TEMPLATE_ERROR;
import static com.boschat.sikb.common.utils.DateUtils.formatFrenchLocalDate;
import static com.boschat.sikb.utils.JsonUtils.jsonNodeToLicenceTypes;

public class PDFGeneratorUtils {

    private static final String JASPER_NAME = "name";

    private static final String JASPER_FIRST_NAME = "firstName";

    private static final String JASPER_SERIES_NUMBER = "licenceNumber";

    private static final String JASPER_BIRTH_DATE = "birthDate";

    private static final String JASPER_LICENCE_TYPE_BACKGROUND = "licenceTypeBackground";

    private static final String JASPER_LICENCE_CARRIER = "licenceType";

    private static final String JASPER_GENDER = "sex";

    private static final String JASPER_VALIDITY_START = "beginDate";

    private static final String JASPER_VALIDITY_END = "endDate";

    private static final String JASPER_CURRENT_YEAR = "currentYear";

    private static final String JASPER_NEXT_YEAR = "nextYear";

    private static final String JASPER_PHOTO = "photo";

    private static final String JASPER_CLUB = "club";

    private static final String JASPER_LOGO = "logo";

    private static final String JASPER_LICENCE_BACKGROUND = "licenceBackground";

    private static final String JASPER_INFORMATION_BACKGROUND = "informationBackground";

    private static Map<Integer, String> colorByType = new HashMap<>();

    private static PDFGeneratorUtils instance;

    private JasperReport jasperFileName;

    private PDFGeneratorUtils() {
        String fileName = JASPER_TEMPLATE_DIRECTORY.getValue() + JASPER_TEMPLATE_LICENCE_NAME.getValue();
        try {
            jasperFileName = JasperCompileManager.compileReport(fileName);

            String[] values = JASPER_TEMPLATE_LICENCE_COLORS_BY_LICENCE_TYPE.getValue().split(";");
            for (int i = 0; i < values.length; i = i + 2) {
                colorByType.put(Integer.parseInt(values[i]), values[i + 1]);
            }
        } catch (Exception e) {
            throw new TechnicalException(JASPER_TEMPLATE_ERROR, e, fileName);
        }
    }

    public static PDFGeneratorUtils getInstance() {
        if (instance == null) {
            instance = new PDFGeneratorUtils();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public void generateLicencePdf(Person person, Club club, Season season, Licence licence, OutputStream out) {
        try {
            Map<String, Object> hm = new HashMap<>();

            hm.put(JASPER_SERIES_NUMBER, licence.getNumber());
            hm.put(JASPER_NAME, person.getName().toUpperCase());
            hm.put(JASPER_FIRST_NAME, person.getFirstname().toUpperCase());

            hm.put(JASPER_GENDER, person.getSex().substring(0, 1));
            hm.put(JASPER_BIRTH_DATE, formatFrenchLocalDate(person.getBirthdate()));

            int nbLicenceType = 1;
            for (LicenceType licenceType : jsonNodeToLicenceTypes(licence.getTypes())) {
                hm.put(JASPER_LICENCE_TYPE_BACKGROUND + (nbLicenceType), getImage(colorByType.get(licenceType.getId())));
                hm.put(JASPER_LICENCE_CARRIER + (nbLicenceType), licenceType.getName());
                nbLicenceType++;
            }

            hm.put(JASPER_VALIDITY_START, formatFrenchLocalDate(season.getBegin()));
            hm.put(JASPER_VALIDITY_END, formatFrenchLocalDate(season.getEnd()));

            hm.put(JASPER_CURRENT_YEAR, String.valueOf(season.getBegin().getYear()));
            hm.put(JASPER_NEXT_YEAR, String.valueOf(season.getEnd().getYear()));

            hm.put(JASPER_PHOTO, getPhoto(person));
            hm.put(JASPER_CLUB, club.getName());

            hm.put(JASPER_LOGO, getImage(JASPER_TEMPLATE_LICENCE_LOGO.getValue()));
            hm.put(JASPER_LICENCE_BACKGROUND, getImage(JASPER_TEMPLATE_LICENCE_BACKGROUND.getValue()));
            hm.put(JASPER_INFORMATION_BACKGROUND, getImage(JASPER_TEMPLATE_LICENCE_INFORMATION_BACKGROUND.getValue()));

            exportPDF(hm, out);
        } catch (Exception e) {
            throw new TechnicalException(EXPORT_PDF_ERROR, e);
        }
    }

    private InputStream getPhoto(Person person) throws IOException {
        byte[] photo = person.getPhotodata();
        if (photo != null && photo.length > 0) {
            return new ByteArrayInputStream(photo);
        } else {
            return getImage(JASPER_TEMPLATE_LICENCE_DEFAULT_PHOTO.getValue());
        }
    }

    private InputStream getImage(String name) throws IOException {
        String path = FilenameUtils.concat(JASPER_TEMPLATE_DIRECTORY.getValue(), name);
        return new FileInputStream(path);
    }

    private void exportPDF(Map<String, Object> hm, OutputStream out) throws JRException {
        JasperPrint jprint = JasperFillManager.fillReport(jasperFileName, hm, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jprint, out);
    }
}
