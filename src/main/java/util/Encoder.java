package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Encoder {

    public static String ftpFileNameEncodeUtf8(String fileName) {
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8);  // EUC-KR로 인코딩
    }
}
