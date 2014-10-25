/**
 *
 */
package com.equivi.mailsy.service.encryption;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.util.Random;


public class PasswordUtil {

    public static final int PASSWORD_MIN_LENGTH = 8;

    private static final String REGEX = "^.*(?=.{8,20})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$";
    private static final Logger logger = Logger.getLogger(PasswordUtil.class);
    private static char[] pwdChars = "abcdefghijklmnopqrstuvqxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    public static char[] generateStrongPassword(int length) {
        while (true) {
            String pwd = new String(generatePassword(length));
            if (pwd.matches(REGEX)) {
                return pwd.toCharArray();
            }
        }
    }

    public static boolean isPasswordComplex(String password) {
        if (!password.matches(REGEX)) {
            return false;
        }
        return true;
    }

    public static String generatePIN(int length) {
        StringBuilder sbPIN = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random r = new Random();
            int randint = r.nextInt(10);
            sbPIN.append(randint);
        }
        return sbPIN.toString();
    }

    private static char[] generatePassword(int length) {
        char[] pwd = new char[length];
        try {
            java.security.SecureRandom random =
                    java.security.SecureRandom.getInstance("SHA1PRNG");

            byte[] intbytes = new byte[4];

            for (int i = 0; i < length; i++) {
                random.nextBytes(intbytes);
                pwd[i] = pwdChars[Math.abs(getIntFromByte(intbytes) % pwdChars.length)];
            }
        } catch (Exception ex) {
            // Don't really worry, we won't be using this if we can't use securerandom anyway
            logger.error(ex.getMessage(), ex);
        }
        return pwd;
    }


    private static int byteToInt(byte b) {
        return (int) b & 0xFF;
    }

    private static int getIntFromByte(byte[] bytes) {
        int returnNumber = 0;
        int pos = 0;
        returnNumber += byteToInt(bytes[pos++]) << 24;
        returnNumber += byteToInt(bytes[pos++]) << 16;
        returnNumber += byteToInt(bytes[pos++]) << 8;
        returnNumber += byteToInt(bytes[pos++]) << 0;
        return returnNumber;
    }

    public static void main(String args[]) {
        while (true) {
            char[] password = PasswordUtil.generateStrongPassword(8);
            String pwd = new String(password);
            boolean valid = pwd.matches(REGEX);
            System.out.println(pwd + " " + ((valid) ? "Pass" : "Fail"));

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String devicePIN = generatePIN(8);
            System.out.println("DEVICE PIN:" + devicePIN);
        }
    }

    public static String getHashedValue(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(input.getBytes("UTF-8"));

            byte raw[] = md.digest();
            String hash = convertToHex(raw);

            return hash;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }


}
