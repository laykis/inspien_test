package util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {

    public static String getNowTime(){
        LocalDateTime now = LocalDateTime.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(now.atZone(defaultZoneId).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
}
