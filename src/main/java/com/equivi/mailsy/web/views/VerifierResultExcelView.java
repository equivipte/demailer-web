package com.equivi.mailsy.web.views;

import com.equivi.mailsy.web.controller.EmailCollectorController;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by tsagita on 23/9/14.
 */
public class VerifierResultExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> emailsResult = (List<String>) model.get(EmailCollectorController.KEY_RESULT_EMAILS);

        HSSFSheet sheet = workbook.createSheet("Emails Verifier Result");

        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Email Address");
        header.createCell(1).setCellValue("First Name");
        header.createCell(2).setCellValue("Last Name");
        header.createCell(3).setCellValue("Company Name");
        header.createCell(4).setCellValue("Address 1");
        header.createCell(5).setCellValue("Address 2");
        header.createCell(6).setCellValue("Address 3");
        header.createCell(7).setCellValue("Country");
        header.createCell(8).setCellValue("City");
        header.createCell(9).setCellValue("Phone");
        header.createCell(10).setCellValue("Zip Code");

        int rowNum = 1;
        for (String email : emailsResult) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(email);
        }

        String fileName = generateFileName();

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }

    private String generateFileName() {
        LocalDate now = new LocalDate();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM_dd_yyyy");

        String fileName = String.format("VerifiedEmails_%s.xls", now.toString(dtf));

        return fileName;
    }

}
