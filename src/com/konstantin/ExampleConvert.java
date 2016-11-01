package com.konstantin;


import java.math.BigInteger;

public class ExampleConvert {
    public static void main(String[] args)
    {
        BigInteger values[] = {new BigInteger("999888777666111"), new BigInteger("5"),
                new BigInteger("15"),new BigInteger(String.valueOf(Long.MAX_VALUE)),
                new BigInteger(String.valueOf(Integer.MAX_VALUE))};
        ConverterNumToWords obj = new ConverterNumToWords();
        for(BigInteger value:values)
        System.out.println(obj.convertNumbToWords(value));
    }
}
