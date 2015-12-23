package kyparus.user;

/**
 * Created by yurii on 29.11.15.
 */
public class Client extends Person {

    private Integer usageTimes = 0;
    private Boolean isRegular = false;

    public void incUsageTimes() {
        usageTimes++;
    }

    public void setUsageTimes(int times) {
        usageTimes = times;
    }

    public Integer getUsageTimes() {
        return usageTimes;
    }

    public Boolean isRegular() {
        return isRegular;
    }

    public void setAsRegular() {
        this.isRegular = true;
    }
}
