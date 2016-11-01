package com.konstantin.test;

import com.konstantin.ConverterNumToWords;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by konstantin on 1.11.16.
 */
public class ConverterNumToWordsTest {


    ConverterNumToWords Converter = new ConverterNumToWords();


    @Test
    public void testConvert_0to19() throws Exception {
        String[] nameNum = new String[]{"ноль","один", "два", "три", "четыре",
                "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать",
                "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};

        System.out.println("Junit test: Convert to words numbers 0-19");

        for (int i = 0; i < 20; i++) {
            System.out.println(i + " = " + Converter.convertNumbToWords(new BigInteger(String.valueOf(i))));
            assertEquals(" Error in the numbers  0-19", nameNum[i],
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(i))));
        }
    }



    @Test
    public void testConvert_20to999() throws Exception {

        String[] nameNum = new String[]{"сорок", "семьдесят один", "сто", "двести два",
                "пятьсот пятьдесят пять", "триста десять","девятьсот девяносто девять"};
        int numbers[] = new int[]{40, 71, 100, 202, 555, 310, 999};

        System.out.println("Junit test: Convert to words numbers 20-999");

        for (int i = 0; i < 7; i++) {
            System.out.println(numbers[i] + " = " +
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(numbers[i]))));
            assertEquals(" Error in the numbers  0-19",nameNum[i],
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(numbers[i]))));
        }
    }


    @Test
    public void testConvert_BigNumber() throws Exception {

        String[] nameNum = new String[]{"два миллиарда сто сорок семь миллионов четыреста восемьдесят три тысячи шестьсот сорок семь",
                "минус один миллион одиннадцать",
                "сто двадцать три миллиона четыреста пятьдесят шесть тысяч семьсот восемьдесят девять"};
        int numbers[] = new int[]{Integer.MAX_VALUE, -1000011, 123456789};

        System.out.println("Junit test: Convert to words big numbers ");

        for (int i = 0; i < 3; i++) {
            System.out.println(numbers[i] + " = " +
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(numbers[i]))));
            assertEquals(" Error in the big numbers",nameNum[i],
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(numbers[i]))));
        }
    }


    @Test
    public void testConvert_genderForms_1and2() throws Exception {

        String[] nameNum = new String[]{"две тысячи", "одна тысяча", "один миллион", "два миллиона"};
        int numbers[] = new int[]{2000, 1000, 1000000, 2000000};

        System.out.println("Junit test: Convert to words number exist units 1/2");

        for (int i = 0; i < 4; i++) {
            System.out.println(numbers[i] + " = " +
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(numbers[i]))));
            assertEquals(" Error in the numbers exist units 1/2",nameNum[i ],
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(numbers[i ]))));
        }
    }



    @Test
    public void testConvert_number_from_excel() throws Exception {
        System.out.println("Junit test: Data Driven Test .Different number from excel file ");
        InputStream in = new FileInputStream("DataTest/TestNumber.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);
        long inNumber = 0;
        String inString = null;
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print((inNumber=(long)cell.getNumericCellValue()) + " = ");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print((inString=cell.getStringCellValue()));
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
            assertEquals("Error in number: " + inNumber, inString,
                    Converter.convertNumbToWords(new BigInteger(String.valueOf(inNumber))));
        }
    }

}