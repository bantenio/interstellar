package org.tenio.interstellar.coder;

public interface CodeConvertor {

    String encode(long number);

    long decode(String code);
}
