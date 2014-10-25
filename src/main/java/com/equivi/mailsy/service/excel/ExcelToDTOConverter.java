package com.equivi.mailsy.service.excel;

import com.equivi.mailsy.dto.contact.ContactDTO;
import com.equivi.mailsy.service.constant.HeaderImportSubscribe;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class ExcelToDTOConverter implements Serializable {

    private static final long serialVersionUID = 1494425012779885879L;

    public ContactDTO convertToContactDTO(Map<String, Integer> mapColumnIndex, ContactDTO contactDTO, Cell cell) {
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.EMAIL_ADDRESS.getHeaderName())) {
            contactDTO.setEmailAddress(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.FIRST_NAME.getHeaderName())) {
            contactDTO.setFirstName(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.LAST_NAME.getHeaderName())) {
            contactDTO.setLastName(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.COMPANY_NAME.getHeaderName())) {
            contactDTO.setCompanyName(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.ADDRESS1.getHeaderName())) {
            contactDTO.setAddress1(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.ADDRESS2.getHeaderName())) {
            contactDTO.setAddress2(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.ADDRESS3.getHeaderName())) {
            contactDTO.setAddress3(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.COUNTRY.getHeaderName())) {
            contactDTO.setCountry(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.CITY.getHeaderName())) {
            contactDTO.setCity(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.PHONE.getHeaderName())) {
            contactDTO.setPhone(cell.getStringCellValue());
        }
        if (cell.getColumnIndex() == mapColumnIndex.get(HeaderImportSubscribe.ZIP_CODE.getHeaderName())) {
            contactDTO.setZipCode(cell.getStringCellValue());
        }


        return contactDTO;
    }
}
