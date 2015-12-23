package kyparus.Tour;

/**
 * Created by yurii on 28.11.15.
 */
public class ShoppingTour extends Tour {


    //LinkedList<String> boutiques;
    String mall;
    Double putativeMoney;
    public String getMall() {
        return mall;
    }

    public void setMall(String mall) {
        this.mall = mall;
    }

    public Double getPutativeMoney() {
        return putativeMoney;
    }

    public void setPutativeMoney(Double putativeMoney) {
        this.putativeMoney = putativeMoney;
    }



    @Override
    public String getInfo() {
        String str = "You will visit the mall: " + mall + ".";
        str += " Putative amount of money to take with is ";
        str += putativeMoney;
        str += " dollars.";

        return str;
    }
}
