package com.equivi.mailsy.service.emailverifier;


import com.equivi.mailsy.dto.contact.ContactDTO;
import com.equivi.mailsy.service.constant.HeaderImportSubscribe;
import com.equivi.mailsy.service.excel.ExcelToDTOConverter;
import gnu.trove.map.hash.THashMap;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ExcelEmailReader {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelEmailReader.class);

    @Resource
    private ExcelToDTOConverter excelToDTOConverter;

    private Map<String, Integer> mapColumnIndex;

    public List<String> getEmailAddressList(final MultipartFile multipartFile) {
        List<String> emailAddressList = new ArrayList<>();

        try {
            Workbook workbook = null;
            if (multipartFile.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(multipartFile.getInputStream());
            } else if (multipartFile.getOriginalFilename().toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(multipartFile.getInputStream());
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

    public List<ContactDTO> getContactDTOList(final MultipartFile multipartFile) {
        List<ContactDTO> contactDTOs = new ArrayList<>();

        try {
            Workbook workbook = null;
            if (multipartFile.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(multipartFile.getInputStream());
            } else if (multipartFile.getOriginalFilename().toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(multipartFile.getInputStream());
            }

            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.iterator();

                mapColumnIndex = new THashMap<>();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    ContactDTO contactDTO = new ContactDTO();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (isHeader(cell)) {
                            setMapColumnIndex(cell);
                        } else {
                            contactDTO = excelToDTOConverter.convertToContactDTO(mapColumnIndex, contactDTO, cell);
                        }
                    }
                    if (contactDTO.getEmailAddress() != null) {
                        contactDTOs.add(contactDTO);
                    }
                }
            }


        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return contactDTOs;
    }

    private void setMapColumnIndex(Cell cell) {

        List<String> headerNames = HeaderImportSubscribe.getHeaderNames();
        for (String headerName : headerNames) {
            if (headerName.equals(cell.getStringCellValue())) {
                mapColumnIndex.put(headerName, cell.getColumnIndex());
            }
        }
    }

    private boolean isHeader(Cell cell) {
        return cell.getRowIndex() == 0;
    }


    private boolean isEmailAddressColumn(Cell cell) {
        return cell.getColumnIndex() == 0 && cell.getRowIndex() > 0 && StringUtils.isNotBlank(cell.getStringCellValue().trim());
    }
}
