import java.io.IOException;

import Controller.Controller;
import Controller.IController;
import GestReviews.GestReviews;
import GestReviews.IGestReviews;
import View.View;

public class GestReviewsAppMVC {
    public static void main(String[] args) {

        IGestReviews gr;
        // load estado de objectStream

        try {
            gr = GestReviews.loadFromBinary("gestReviews.dat");
        } catch (ClassNotFoundException | IOException e) {
            gr = new GestReviews();
        }
        IController controller = new Controller(gr);
        View v = new View(controller);
        v.run();

        try {
            gr.saveToBinary("gestReviews.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
