package org.tenio.interstellar.coder;

public class ScaleCodeConvertor implements CodeConvertor {

    private char[] table;

    private String tableStr;

    private int scale;

    public ScaleCodeConvertor(String tableString) {
        tableStr = tableString;
        table = tableString.toCharArray();
        scale = table.length;
    }

    @Override
    public String encode(long number) {
        StringBuilder builder = new StringBuilder();
        int remainder;
        int condition = scale - 1;
        while (number > condition) {
            remainder = Long.valueOf(number % scale).intValue();
            builder.append(table[remainder]);
            number = number / scale;
        }
        builder.append(table[Long.valueOf(number).intValue()]);
        return builder.reverse().toString();
    }

    @Override
    public long decode(String code) {
        long value = 0;
        char tempChar;
        char[] codeChars = code.toCharArray();
        int tempCharValue, codeLen = codeChars.length;
        for (int i = 0; i < codeLen; i++) {
            tempChar = codeChars[i];
            tempCharValue = tableStr.indexOf(tempChar);
            value += (long) (tempCharValue * Math.pow(scale, codeLen - i - 1));
        }
        return value;
    }
}
