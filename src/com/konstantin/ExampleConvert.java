package com.konstantin;


import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExampleConvert {
    private static Logger log = Logger.getLogger(ExampleConvert.class.getName());

    public static void main(String[] args) {
        BigInteger values[] = {new BigInteger("99988877766611178587876868578858685768"), new BigInteger("5"),
                new BigInteger("-15"), new BigInteger(String.valueOf(Long.MAX_VALUE)),
                new BigInteger(String.valueOf(Integer.MAX_VALUE))};
        ConverterNumToWords obj = new ConverterNumToWords();
        for (BigInteger value : values)
            try {
                System.out.println(obj.convertNumbToWords(value));
            } catch (IOException e) {
                log.log(Level.SEVERE, null, e);
            }
    }
}
