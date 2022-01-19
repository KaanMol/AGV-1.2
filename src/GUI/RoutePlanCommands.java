package GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RoutePlanCommands {

    public void backSpace(Stage stage, ArrayList<String> route, ArrayList<Integer> routeCommands){
        route.remove(route.size() - 1);
        routeCommands.remove(routeCommands.size() - 1);
        //this.arrowRoute.remove(this.arrowRoute.size() - 1);

        //this.routePlanScene(stage, this.routeCommands);
    }

    public void image(GridPane pane)throws IOException {
        FileInputStream inputstream = new FileInputStream("src\\GUI\\image\\Barts_Bakkerij_logo.png");
        Image image = new Image(inputstream);
        ImageView view = new ImageView(image);
        pane.add(view, 1, 0);
    }

    public void displayRoute(GridPane pane, ArrayList<String> route){
        int l = 0;

        while (l < route.size()) {
            for (int i = 0; (i <= route.size() / 5); i++) {
                for (int c = 0; c < 5; c++) {
                    if(l < route.size() && l < 155) {
                        Label label = new Label(route.get(l));
                        pane.add(label, i, c);
                    }
                    l++;
                }
            }
        }
    }
}
