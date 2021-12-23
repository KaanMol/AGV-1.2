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
        commands.openPort();

        GridPane pane = new GridPane();
        BorderPane borderPane = new BorderPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Button forward = new Button("^" );
        forward.setOnAction(movingForward -> {
            commands.forwardbutton();
        });

        Button left = new Button("<" );
        left.setOnAction(movingLeft -> {
            commands.leftButton();
        });

        Button right = new Button(">");
        right.setOnAction(movingRight -> {
            commands.rightButton();
        });

        Button backward = new Button("|");
        backward.setOnAction(movingBackward -> {
            commands.backwardButton();
        });


        pane.add(forward, 10, 10);

        pane.add(left, 9, 11);

        pane.add(right, 11, 11);

        pane.add(backward, 10, 12);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    public static void main() {
        launch(Layout.class);

    }
}


