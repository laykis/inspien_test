package util;

import constant.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Base64;

public class Decoder {

    private static final Logger logger = LoggerFactory.getLogger(Decoder.class);

    public static String decodeXml(String base64Encoded){
        return decodeBase64(base64Encoded, Const.EUC_KR);
    }

    public static String decodeJson(String base64Encoded){
        return decodeBase64(base64Encoded, Const.UTF_8);
    }

    private static String decodeBase64(String base64Data, String charsetName) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            return new String(decodedBytes, Charset.forName(charsetName));
        } catch (Exception e) {
            logger.error("Error while decoding Base64", e);
            logger.error(e.getMessage());
            return null;
        }
    }
}
