package com.konstantin.test;

import com.konstantin.ConverterNumToWords;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Created by konstantin on 14.11.16.
 */
public class NewConverterNumToWordsTest {

    private final ConverterNumToWords Converter = new ConverterNumToWords();

    private final String[][] testNumber = {{"0", "ноль"}, {"1", "один"},
            {"2", "два"}, {"5", "пять"}, {"15", "пятнадцать"},
            {"72", "семьдесят два"}, {"310", "триста десять"}, {"1000", "одна тысяча"}, {"2000", "две тысячи"},
            {"123456789", "сто двадцать три миллиона четыреста пятьдесят шесть тысяч семьсот восемьдесят девять"}};

    @Test(expected = NullPointerException.class)
    public void convertNumbToWords() {
        Converter.convertNumbToWords(new BigInteger("41242141242142142142421421421421421421" +
                "42142141242142142142142142142141241241242121421412412"));
    }

    @Test
    public void testConvertNumberToWords()throws Exception
    {
       for(String [] number:testNumber){
           assertEquals(number[1],
                   Converter.convertNumbToWords(new BigInteger(number[0])));
       }
    }



}