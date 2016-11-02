package com.konstantin;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConverterNumToWords {

    private final static String PATH_FILE = "dataName/nameUnits.txt";
    private final static int MALE_GENDER = 1;
    private final static int FEMALE_GENDER = -1;
    private final static String SEPARATOR = " ";

    /**
     * Данный мап хранит все возможные наименования для составления имени триад .
     * Ключём являются числовые представления .
     */
    private static final Map<Integer, String> nameTriad = new HashMap<Integer, String>() {{
        put(-2, "две");
        put(-1, "одна");
        put(0, "ноль");
        put(1, "один");
        put(2, "два");
        put(3, "три");
        put(4, "четыре");
        put(5, "пять");
        put(6, "шесть");
        put(7, "семь");
        put(8, "восемь");
        put(9, "девять");
        put(10, "десять");
        put(11, "одиннадцать");
        put(12, "двенадцать");
        put(13, "тринадцать");
        put(14, "четырнадцать");
        put(15, "пятнадцать");
        put(16, "шестнадцать");
        put(17, "семнадцать");
        put(18, "восемнадцать");
        put(19, "девятнадцать");
        put(20, "двадцать");
        put(30, "тридцать");
        put(40, "сорок");
        put(50, "пятьдесят");
        put(60, "шестьдесят");
        put(70, "семьдесят");
        put(80, "восемьдесят");
        put(90, "девяносто");
        put(100, "сто");
        put(200, "двести");
        put(300, "триста");
        put(400, "четыреста");
        put(500, "пятьсот");
        put(600, "шестьсот");
        put(700, "семьсот");
        put(800, "восемьсот");
        put(900, "девятьсот");

    }};

    /**
     * Мап для хранения  наименования чисел состоящих из латинского имени степени тысячи .
     * Ключём служит степень.
     * Тысяча внесена как исключение. Заполняется из файла в конструкторе
     */
    private Map<Integer, String> nameUnits = new HashMap<Integer, String>() {{
        put(1, "тысяч");
    }};

    /**
     * Окочания для тысяч- и -иллионов/-ардов соответственно.
     */
    private final String[][] endings = {{"а", "и", ""}, {"", "а", "ов"}};


    public ConverterNumToWords() {

        readNameUnitFromFile();

    }

    /**
     * Чтение из файла и запись наименований в nameUnits.
     */
    private void readNameUnitFromFile() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH_FILE), "UTF8"))) {
            String tmpLineStr;
            String Units[];
            while ((tmpLineStr = br.readLine()) != null) {
                Units = tmpLineStr.split(SEPARATOR);
                nameUnits.put(Integer.valueOf(Units[0]), Units[1]);
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Конвертирует триады(числа до трёх знаков) в пропись.
     * Принимает триаду как строку и константу для вычисления формы 1/2 по их роду.
     */
    private String convertTriad(String triad, int gender) {
        int hundreds = Character.getNumericValue(triad.charAt(0));
        int dozens = Character.getNumericValue(triad.charAt(1));
        int units = Character.getNumericValue(triad.charAt(2));
        if (Objects.equals(triad, "000")) return "";
        String words = "";
        /*Триады высчитываются путём умножения значений порядков(сотни,десятки,еденицы)на 100/10/1
         соответственно и взятием по этому ключу имени порядка*/
        if (hundreds != 0)
            words += nameTriad.get(hundreds * 100) + SEPARATOR;
        if (dozens == 1 && units <= 9 && units >= 0)   //Для чисел от 10 до 19 ключи для мапа считаются отдельно
            words += nameTriad.get(dozens * 10 + units) + SEPARATOR;
        else {
            if (dozens != 0)
                words += nameTriad.get(dozens * 10) + SEPARATOR;
            if (units != 0 && units > 2)
                words += nameTriad.get(units) + SEPARATOR;
            /*Если в еденицах числа имеется 1 или 2 , то ключ для мапа умножается на переданную константу
             gender. MALE_GENDER является 1 и значение ключа не меняется - один/два.
             FEMALE_GENDER является -1 и значения инвентируются в -1/-2 что есть женские формы одна/две.*/
            else if (units != 0 && units <= 2)
                words += nameTriad.get(units * gender) + SEPARATOR;
        }
        return words;
    }

    /**
     * Получаем номер окончания  для названия из мап nameUnit, основываясь на числе n ,в массиве ending
     */
    private int selectForm(int n) {
        n = Math.abs(n) % 100;
        int n1 = n % 10;
        if (n > 10 && n < 20) return 2;
        if (n1 > 1 && n1 < 5) return 1;
        if (n1 == 1) return 0;
        return 2;
    }

    /**
     * Если название числа со степенью тысячи degree существует и строка number не равна "000" , то возвращает
     * название с окончанием основаным на числе number.
     */
    private String getFormNameUnit(int degree, String number) {
        if (nameUnits.get(degree) == null && degree != 0) throw new NullPointerException("Dont exist name " +
                degree + " thousands of degrees");
        if (Objects.equals(number, "000")) return "";
        if (degree == 1) return nameUnits.get(degree) + endings[0][selectForm(Integer.parseInt(number))] + SEPARATOR;
        if (degree > 1) return nameUnits.get(degree) + endings[1][selectForm(Integer.parseInt(number))] + SEPARATOR;
        else
            return "";
    }

    /**
     * Конвертирует число number в запись словами
     */
    public String convertNumbToWords(BigInteger number) {
        String numberStr = number.toString();

        if (Objects.equals(numberStr, "0")) return nameTriad.get(0);

        String nameNumber = "";

        /* Если есть минус добовляем к итоговой строкеи удаляем минус из строки */
        if (numberStr.charAt(0) == '-') {
            nameNumber += "минус ";
            numberStr = numberStr.substring(1);
        }

        /* Дополняет строку нялми до кратности 3 для удобной работы с подстроками по 3 символа*/
        for (int i = 0; i < numberStr.length() % 3; i++)
            numberStr = "0" + numberStr;

        /*
          Конвертирует в слова ,попорядку по всей строке, группу из 3 цифр и добовляет имя порядка из
          мап nameUnit .Если степень 1(тысячи) передаём константу FEMALE_GENDER для конвертирования 1/2
          в форму женского рода , иначе передаётся MALE_GENDER
          */
        for (int i = 0; i < numberStr.length() / 3; i++) {
            int degree = numberStr.length() / 3 - i - 1;
            if (degree == 1)
                nameNumber += convertTriad(numberStr.substring((i * 3), i * 3 + 3), FEMALE_GENDER) +
                        getFormNameUnit(degree, numberStr.substring((i * 3), i * 3 + 3));
            else
                nameNumber += convertTriad(numberStr.substring((i * 3), i * 3 + 3), MALE_GENDER) +
                        getFormNameUnit(degree, numberStr.substring((i * 3), i * 3 + 3));
        }
        return nameNumber.trim();
    }


}
