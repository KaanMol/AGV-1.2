package GUI;

import common.WirelessConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vehicle.Movement;

import java.awt.*;

public class Layout extends Application {

    @Override
    public void start(Stage stage) {

        Commands commands = new Commands();

        GridPane pane = new GridPane();
        BorderPane borderPane = new BorderPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Button forward = new Button("^" );
        forward.setOnAction(movingForward -> {
            commands.forwardbutton();
        });

        Button left = new Button("<" );
        Button right = new Button(">");
        Button backward = new Button("|");



        pane.add(forward, 2, 1);


        pane.add(left, 1, 2);


        pane.add(right, 3, 2);


        pane.add(backward, 2, 3);



        //borderPane.setCenter(new Text);



//        Button button = new Button(new Text("hello"));



        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    public static void main() {
        launch(Layout.class);
    }
}


