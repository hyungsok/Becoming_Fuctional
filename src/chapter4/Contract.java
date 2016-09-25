package chapter4;

import java.util.Calendar;

public class Contract {

  public final Calendar begin_date;
  public final Calendar end_date;
  public final Boolean enabled;

  public Contract(Calendar begin_date, Boolean enabled) {
    this.begin_date = begin_date;
    this.end_date = this.begin_date.getInstance();
    this.end_date.setTimeInMillis(this.begin_date.getTimeInMillis());
    this.end_date.add(Calendar.YEAR, 2);
    this.enabled = enabled;
  }

}