package com.equivi.demailer.service.constant;


public enum SystemStateKey {

    SETTLEMENT_MCD_RUN_FOR_THE_DAY("settlement.mcd.run");


    private String keyName;

    SystemStateKey(String keyName) {

        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
