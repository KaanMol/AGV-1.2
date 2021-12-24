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

        Button forward = new Button("↑" );
        forward.setPrefSize(60, 60);
        forward.setOnAction(movingForward -> {
            commands.forwardbutton();
        });

        Button left = new Button("←" );
        left.setPrefSize(60, 60);
        left.setOnAction(movingLeft -> {
            commands.leftButton();
        });

        Button right = new Button("→");
        right.setPrefSize(60, 60);
        right.setOnAction(movingRight -> {
            commands.rightButton();
        });

        Button backward = new Button("↓");
        backward.setPrefSize(60, 60);
        backward.setOnAction(movingBackward -> {
            commands.backwardButton();
        });

        Button neutral = new Button("o");
        neutral.setPrefSize(60, 60);
        backward.setOnAction(notMoving -> {
            commands.neutralButton();
        });

        Button emergencyStop = new Button("Noodstop");
        emergencyStop.setPrefSize(150, 60);
        emergencyStop.setOnAction(immediateStop -> {
            commands.emergencyButton();
        });

        Button gripper = new Button("Gripper");
        gripper.setPrefSize(100, 60);
        gripper.setOnAction(gripperMove -> {
            commands.gripperButton();
        });

        pane.add(forward, 8, 72);

        pane.add(left, 5, 75);

        pane.add(right, 11, 75);

        pane.add(backward, 8, 78);

        pane.add(neutral, 8, 75);

        pane.add(emergencyStop, 16, 75);

        pane.add(gripper, 135, 75);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    public static void main() {
        launch(Layout.class);

    }
}


