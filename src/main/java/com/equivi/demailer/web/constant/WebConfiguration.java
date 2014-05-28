package com.equivi.demailer.web.constant;


import com.equivi.demailer.service.constant.dEmailerWebPropertyKey;
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
}
