package kyparus.user;

/**
 * Created by yurii on 29.11.15.
 */
public class Agent extends Person {

    private String position;
    private Double salary;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
