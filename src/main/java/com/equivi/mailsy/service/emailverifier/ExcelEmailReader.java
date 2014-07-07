package com.equivi.mailsy.service.emailverifier;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelEmailReader {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelEmailReader.class);

    public List<String> getEmailAddressList(final String fileName) {
        List<String> emailAddressList = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (fileName.toLowerCase().equals(".xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            }

            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (isEmailAddressColumn(cell)) {
                            String email = cell.getStringCellValue().trim();
                            emailAddressList.add(email);
                        }
                    }
                }
            }


        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return emailAddressList;
    }

    private boolean isEmailAddressColumn(Cell cell) {
        return cell.getColumnIndex() == 0 && cell.getRowIndex() > 0 && StringUtils.isNotBlank(cell.getStringCellValue().trim());
    }
}
