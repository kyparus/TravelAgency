package kyparus.Tour;

/**
 * Created by yurii on 28.11.15.
 */
public class ExcursionTour extends Tour {

    //LinkedList<String> landmarks;
    String landmark;
    String description;

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String shortDescription) {
        this.description = shortDescription;
    }


    @Override
    public String getInfo() {
        StringBuilder str = new StringBuilder("You will visit such landmark: " + landmark + ". ");

        str.append("Description: ");
        str.append(description);

//        int i = landmarks.size();
//        for(String s : landmarks) {
//            i--;
//            str.append(s);
//            if(i != 0)
//                str.append(", ");
//            else str.append(".\n");
//        }
        return str.toString();
    }
}
