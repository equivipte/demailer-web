package com.equivi.mailsy.web.constant;


import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.util.WebConfigUtil;
import com.equivi.mailsy.web.exception.InvalidConfigException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class WebConfiguration implements Serializable {

    private static final long serialVersionUID = -2097132554265613355L;

    private static final Integer PAGE_LIMIT_DEFAULT = 10;

    public Integer getMaxRecordsPerPage() {
        String pageRecords = WebConfigUtil.getValue(dEmailerWebPropertyKey.PAGING_MAX_RECORDS);

        if (StringUtils.isBlank(pageRecords)) {
            return PAGE_LIMIT_DEFAULT;
        }
        return Integer.parseInt(pageRecords);
    }

    public String getEmailVerifierLocation() {
        String emailVerifierLocation = WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_VERIFIER_IMPORT_LOCATION);
        if(StringUtils.isBlank(emailVerifierLocation)){
           throw new InvalidConfigException("Unable to find import email verifier location");
        }
        return emailVerifierLocation;
    }
}
