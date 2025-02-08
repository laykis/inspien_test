package util;

import java.nio.charset.StandardCharsets;

public class Encoder {


    public static String encodeToUtf8(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.UTF_8);  // EUC-KR로 인코딩
    }

    public static String encodeToISO88591(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }


}
