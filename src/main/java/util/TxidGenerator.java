package util;

import java.util.UUID;

public class TxidGenerator {

    public static String getTxid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
