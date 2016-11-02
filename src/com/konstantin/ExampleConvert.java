package com.konstantin;


import java.math.BigInteger;

public class ExampleConvert {
    public static void main(String[] args)
    {
        BigInteger values[] = {new BigInteger("99988877766611178587876868578858685768"), new BigInteger("5"),
                new BigInteger("15"),new BigInteger(String.valueOf(Long.MAX_VALUE)),
                new BigInteger(String.valueOf(Integer.MAX_VALUE))};
        ConverterNumToWords obj = new ConverterNumToWords();
        for(BigInteger value:values)
        System.out.println(obj.convertNumbToWords(value));
    }
}
