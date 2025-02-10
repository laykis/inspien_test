package util;


import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeUtilTest {

    @Test
    void timeZoneTest(){

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        System.out.println("현재 시스템 시간대: " + ZoneId.systemDefault());

        Timestamp timeStamp = TimeUtil.getTimestamp();
        ZonedDateTime utcTime = timeStamp.toInstant().atZone(ZoneId.of("Asia/Seoul"));

        ZonedDateTime systemTime = ZonedDateTime.now(ZoneId.systemDefault());

        System.out.println("현 시스템 시간: " + systemTime);
        System.out.println("Asia/Seoul 변환된 시간: " + utcTime);

        long diffInSeconds = Math.abs(utcTime.toEpochSecond() - systemTime.toEpochSecond());
        assertTrue(diffInSeconds < 2, "Asia/Seoul 시간 에러");

    }

}