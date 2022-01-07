package GUI;

import common.WirelessConfig;
import enums.Direction;
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
import java.util.ArrayList;

public class Layout extends Application {
    private Commands commands;
    private ArrayList<Direction> route;
    private boolean gripperClosed;

    @Override
    public void start(Stage stage) {
        commands = new Commands();
        commands.openPort();
        gripperClosed = true;

        controlScene(stage);
    }

    public static void main() {
        launch(Layout.class);
    }


    public void routePlanScene(Stage stage){
        GridPane pane = new GridPane();
        BorderPane borderPane = new BorderPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Button forward = new Button("↑" );
        forward.setPrefSize(60, 60);
        forward.setOnAction(movingForward -> {
            route.add(Direction.FORWARD);
        });

        Button left = new Button("←" );
        left.setPrefSize(60, 60);
        left.setOnAction(movingLeft -> {
            route.add(Direction.LEFT);
        });

        Button right = new Button("→");
        right.setPrefSize(60, 60);
        right.setOnAction(movingRight -> {
            route.add(Direction.RIGHT);
        });

        Button backward = new Button("↓");
        backward.setPrefSize(60, 60);
        backward.setOnAction(movingBackward -> {
            //boebot mag niet achteruit rijden
            route.add(Direction.BACKWARD);
        });

        Button neutral = new Button("o");
        neutral.setPrefSize(60, 60);
        backward.setOnAction(notMoving -> {
            route.add(Direction.NEUTRAL);
        });

        Button emergencyStop = new Button("Noodstop");
        emergencyStop.setPrefSize(150, 60);
        emergencyStop.setOnAction(immediateStop -> {
            commands.emergencyButton();
        });

        Button gripperOut = new Button("Gripper open");
        gripperOut.setPrefSize(100, 60);
        gripperOut.setOnAction(gripperMove -> {
            route.add(Direction.PICKUP);
        });

        Button gripperIn = new Button("Gripper dicht");
        gripperIn.setPrefSize(100, 60);
        gripperIn.setOnAction(gripperMove -> {
            route.add(Direction.PUTDOWN);
        });

        Button startRoute = new Button("Start route");
        startRoute.setPrefSize(100, 60);
        startRoute.setOnAction(gripperMove -> {

        });

        Button switchScene = new Button("Route plannen");
        switchScene.setPrefSize(300, 60);
        switchScene.setOnAction(swithcingScene -> {
            this.routePlanScene(stage);
        });

        Button switchScene2 = new Button("Besturen");
        switchScene.setPrefSize(300, 60);
        switchScene.setOnAction(swithcingScene -> {
            this.controlScene(stage);
        });


        pane.add(forward, 8, 72);

        pane.add(left, 5, 75);

        pane.add(right, 11, 75);

        pane.add(backward, 8, 78);

        pane.add(neutral, 8, 75);

        pane.add(emergencyStop, 16, 75);

        pane.add(gripperIn, 90, 75);
        pane.add(gripperOut, 95, 75);

        pane.add(startRoute, 0, 75);

        pane.add(switchScene, 30, 0);
        pane.add(switchScene2, 0, 0);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void controlScene(Stage stage){
        GridPane pane = new GridPane();
        BorderPane borderPane = new BorderPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Button forward = new Button("↑" );
        forward.setPrefSize(60, 60);
        forward.setOnAction(movingForward -> {
            System.out.println("jda;");
            //commands.forwardbutton();
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

        Button switchScene = new Button("Route plannen");
        switchScene.setPrefSize(300, 60);
        switchScene.setOnAction(swithcingScene -> {
            this.routePlanScene(stage);
        });

        Button switchScene2 = new Button("Besturen");
        switchScene2.setPrefSize(300, 60);
        switchScene2.setOnAction(swithcingScene -> {
            this.controlScene(stage);
        });


        pane.add(forward, 8, 72);

        pane.add(left, 5, 75);

        pane.add(right, 11, 75);

        pane.add(backward, 8, 78);

        pane.add(neutral, 8, 75);

        pane.add(emergencyStop, 16, 75);

        pane.add(gripper, 135, 75);

        pane.add(switchScene, 0, 0);
        pane.add(switchScene2, 30, 0);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

}


