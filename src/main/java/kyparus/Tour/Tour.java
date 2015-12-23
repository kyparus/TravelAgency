package kyparus.Tour;

/**
 * Created by yurii on 27.11.15.
 */


public abstract class Tour {

    private String name;

    private String transToLocation;
    private String transFromLocation;


    private Integer ID;
    private Integer durationFrom; //hours
    private Integer durationTo;   //hours
    private String departure;    // dd/mm/yyyy
    private String arrival;      // dd/mm/yyyy
    private Double price;
    private Boolean isHot = false;

    public Integer getID() {return ID;}

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTransToLocation() {
        return transToLocation;
    }

    public void setTransToLocation(String transToLocation) {
        this.transToLocation = transToLocation;
    }

    public String getTransFromLocation() {
        return transFromLocation;
    }

    public void setTransFromLocation(String transFromLoaction) {
        this.transFromLocation = transFromLoaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isHot() {
        return isHot;
    }

    public void setHot(Boolean isHot) {
        this.isHot = isHot;
    }


    public Integer getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(Integer durationFrom) {
        this.durationFrom = durationFrom;
    }

    public Integer getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(Integer durationTo) {
        this.durationTo = durationTo;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String  departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public abstract String getInfo();
}
