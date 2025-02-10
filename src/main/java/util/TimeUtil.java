package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class TimeUtil {

    public static String getNowTime(){
        LocalDateTime now = LocalDateTime.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(now.atZone(defaultZoneId).withZoneSameInstant(ZoneId.of("Asia/Seoul")).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static Timestamp getTimestamp(){
        ZonedDateTime nowUtc = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        return Timestamp.from(nowUtc.toInstant());
    }
}
