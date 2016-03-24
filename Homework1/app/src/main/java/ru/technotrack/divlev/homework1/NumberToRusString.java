package ru.technotrack.divlev.homework1;


public class NumberToRusString {
    private NumberToRusString() {};

    static public String convert(int number) throws IllegalArgumentException{
        StringBuilder res = new StringBuilder();

        if (number > 1000000 || number < 1) {
            throw new IllegalArgumentException();
        }

        if (number == 1000000) {
            return "один миллион";
        }

        int thousands = number / 1000;
        number %= 1000;

        if ( thousands > 0) {
            res.append(convertNumberLess1000(thousands, true));
            res.append(" ");
            res.append(getThousandsFormat(thousands));
            if (number > 0) {
                res.append(" ");
            }
        }

        res.append(convertNumberLess1000(number, false));

        return res.toString();
    }

    static private StringBuilder convertNumberLess1000(int number, boolean is_thousands) {
        StringBuilder res = new StringBuilder();

        if (number < 0 || number > 999) {
            throw new IllegalArgumentException();
        }

        if (number == 0) {
            return res;
        }

        switch (number / 100) {
            case 0:
                break;
            case 1:
                res.append("сто");
                break;
            case 2:
                res.append("двести");
                break;
            case 3:
                res.append("триста");
                break;
            case 4:
                res.append("четыреста");
                break;
            default:
                res.append(convertNumberLess10(number / 100, false));
                res.append("сот");
        }
        if (number % 100 > 0) {
            res.append(" ");
            res.append(convertNumberLess100(number % 100, is_thousands));
        }
        return res;
    }

    static private String convertNumberLess100(int number, boolean is_thousands) {
        StringBuilder res = new StringBuilder();
        String[] between10And19 = new String[] {"десять", "одиннадцать", "двенадцать", "тринадцать",
            "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};

        if (number < 1 || number > 100) {
            throw new IllegalArgumentException();
        }

        if (number > 9 && number < 20) {
            return between10And19[number - 10];
        }

        switch (number / 10) {
            case 0:
                break;
            case 2:
            case 3:
                res.append(convertNumberLess10(number / 10, false));
                res.append("дцать");
                break;
            case 4:
                res.append("сорок");
                break;
            case 9:
                res.append("девяносто");
                break;
            default:
                res.append(convertNumberLess10(number / 10, false));
                res.append("десят");
        }

        if (number % 10 > 0) {
            if (number / 10 > 0) {
                res.append(" ");
            }
            res.append(convertNumberLess10(number % 10, is_thousands));
        }

        return res.toString();
    }

    static private String convertNumberLess10(int number, boolean is_thousands) {
        if (number < 1 || number > 9) {
            throw new IllegalArgumentException();
        }

        if (is_thousands && number < 3) {
            return number == 1 ? "одна" : "две";
        }

        String[] numbersLess10 = new String[] {"один", "два", "три", "четыре", "пять", "шесть",
                "семь", "восемь", "девять"};

        return numbersLess10[number - 1];
    }

    static private String getThousandsFormat(int thousands) {
        if (thousands < 1 || thousands > 999) {
            throw new IllegalArgumentException();
        }

        if (thousands % 100 > 4 && thousands % 100 < 21)
            return "тысяч";
        if (thousands % 10 == 1)
            return "тысяча";
        if (thousands % 10 > 1 && thousands % 10 < 5)
            return "тысячи";

        return "тысяч";
    }
}
