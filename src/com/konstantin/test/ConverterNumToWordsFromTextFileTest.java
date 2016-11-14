package com.konstantin.test;

import com.konstantin.ConverterNumToWords;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Objects;

import static org.junit.Assert.*;


public class ConverterNumToWordsFromTextFileTest {

    private BufferedReader br = null;
    private final ConverterNumToWords Converter = new ConverterNumToWords();

    @Before
    public void setUp() throws IOException {
        br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("DataTest/TestNumber.txt"), "UTF8"));
    }

    @After
    public void tearDown() throws IOException {
        if (br != null) {
            br.close();
        }
        br = null;
    }


    @Test
    public void testConvertDataFromFile() throws IOException {
        String tmpLineStr;
        String Units[];
        while ((tmpLineStr = br.readLine()) != null) {
            if (!Objects.equals(tmpLineStr, "")) {
                Units = tmpLineStr.split(":");
                assertEquals(Units[1],
                        Converter.convertNumbToWords(new BigInteger(Units[0])));
            }
        }
    }
}