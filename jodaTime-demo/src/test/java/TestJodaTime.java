import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Date;

/**
 * @author luwenlong
 * @date 2017/11/15 0015
 * @description jodaTime API demo
 */

public class TestJodaTime {

    @Test
    public void dayFromFirstDayOfCurrentMonth() {
        DateTime dateTime = new DateTime();
        DateTime startTimeOfMonth = dateTime.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withMillisOfSecond(0);
        System.out.println(startTimeOfMonth);//当前月的第一天0点0分0秒
        DateTime startTime = startTimeOfMonth.plusDays(0);
        System.out.println(startTime);
    }

    @Test
    public void dayFromLastDayOfCurrentMonth(){
        DateTime dateTime = new DateTime();
        DateTime endTime = dateTime
                .withDayOfMonth(1)
                .withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0).plusMonths(1).minusDays(2);
        System.out.println(endTime.toDateTime().toString("yyyy-MM-dd:HH:mm:ss"));
    }

    @Test
    public void testNull(){
        System.out.println(new DateTime().isAfter(null));
    }

}
