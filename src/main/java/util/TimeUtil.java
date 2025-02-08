package util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {

    public static String getFileNameFormatTime(){
        // LocalDateTime을 Date로 변환
        LocalDateTime now = LocalDateTime.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(now.atZone(defaultZoneId).toInstant());

        // SimpleDateFormat을 사용하여 날짜 포맷
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(date);
    }
}
