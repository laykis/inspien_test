package util;

import java.nio.charset.Charset;
import java.util.Base64;

public class Decoder {

    public static String decodeXml(String base64Encoded){
        return decodeBase64(base64Encoded, "EUC-KR");
    }

    public static String decodeJson(String base64Encoded){
        return decodeBase64(base64Encoded, "UTF-8");
    }

    private static String decodeBase64(String base64Data, String charsetName) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            return new String(decodedBytes, Charset.forName(charsetName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
