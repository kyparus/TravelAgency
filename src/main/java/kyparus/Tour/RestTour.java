package kyparus.Tour;

/**
 * Created by yurii on 28.11.15.
 */
public class RestTour extends Tour {


    public enum Board {
        RO, BB, HB,
        FB, AI, UAI}
    private String hotel;
    private Board board;

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }



    @Override
    public String getInfo() {
        StringBuilder str = new StringBuilder("You wil take pleasure in ");
        str.append(hotel);
        str.append(" hotel with ");
        str.append(board.toString() + " board.");
        return str.toString();
    }



}
