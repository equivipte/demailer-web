package com.equivi.mailsy.web.constant;


import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.web.exception.InvalidConfigException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Properties;

@Component
public class WebConfiguration implements Serializable {

    private static final long serialVersionUID = -2097132554265613355L;

    private static final Integer PAGE_LIMIT_DEFAULT = 10;

    @Resource(name = "dEmailerWebProperties")
    private Properties dEmailerWebProperties;

    public Integer getMaxRecordsPerPage() {
        String pageRecords = dEmailerWebProperties.getProperty(dEmailerWebPropertyKey.PAGING_MAX_RECORDS.getKeyName());

        if (StringUtils.isBlank(pageRecords)) {
            return PAGE_LIMIT_DEFAULT;
        }
        return Integer.parseInt(pageRecords);
    }

    public String getEmailVerifierLocation() {
        String emailVerifierLocation = dEmailerWebProperties.getProperty(dEmailerWebPropertyKey.EMAIL_VERIFIER_IMPORT_LOCATION.getKeyName());
        if(StringUtils.isBlank(emailVerifierLocation)){
           throw new InvalidConfigException("Unable to find import email verifier location");
        }
        return emailVerifierLocation;
    }
}
