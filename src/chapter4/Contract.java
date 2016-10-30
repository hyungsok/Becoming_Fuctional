package chapter4;

import java.util.Calendar;

/**
 * 계약
 */
public class Contract {

    // 시작일자 : 계약이 발표되기 시작한 날짜
    public final Calendar begin_date;
    // 종료일자 : 계약이 만료되는 날짜
    public final Calendar end_date;
    // 유요여부 ( true : 유효계약, false : 해지계약 )
    public final Boolean enabled;

    public Contract(Calendar begin_date, Boolean enabled) {
        this.begin_date = begin_date;
        this.end_date = Calendar.getInstance();
        this.end_date.setTimeInMillis(this.begin_date.getTimeInMillis());
        this.end_date.add(Calendar.YEAR, 2);
        this.enabled = enabled;
    }

}