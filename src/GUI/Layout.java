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
import javafx.scene.control.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Layout extends Application {
    private Commands commands;
    private ArrayList<String> route;
    private boolean gripperClosed;

    @Override
    public void start(Stage stage) {
        this.route = new ArrayList<String>();
        commands = new Commands();
        //commands.openPort();
        gripperClosed = true;

        this.controlScene(stage);
    }

    public static void main() {
        launch(Layout.class);
    }

    public void displayRoute(GridPane pane){
        //this.routePlanScene(stage);
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

    //Gui layout for the route planning page
    public void routePlanScene(Stage stage, ArrayList<String> route){
        GridPane pane = new GridPane();
        GridPane routePane = new GridPane();
        routePane.setHgap(30);
        pane.setHgap(10);
        pane.setVgap(10);

        Button forward = new Button("↑" );
        forward.setPrefSize(60, 60);
        forward.setOnAction(movingForward -> {
            route.add("Vooruit ↑");
            this.routePlanScene(stage, this.route);
        });

        Button left = new Button("←" );
        left.setPrefSize(60, 60);
        left.setOnAction(movingLeft -> {
            route.add("Links ←");
            this.routePlanScene(stage, this.route);
        });

        Button right = new Button("→");
        right.setPrefSize(60, 60);
        right.setOnAction(movingRight -> {
            route.add("Rechts →");
            this.routePlanScene(stage, this.route);
        });

        Button backward = new Button("↓");
        backward.setPrefSize(60, 60);
        backward.setOnAction(movingBackward -> {
            //boebot mag niet achteruit rijden
            route.add("Achteruit ↓");
            this.routePlanScene(stage, this.route);
        });

        Button neutral = new Button("o");
        neutral.setPrefSize(60, 60);
        backward.setOnAction(notMoving -> {
            route.add("Neutraal ");
            this.routePlanScene(stage, this.route);
        });

        Button emergencyStop = new Button("Noodstop");
        emergencyStop.setPrefSize(150, 60);
        emergencyStop.setOnAction(immediateStop -> {
            commands.emergencyButton();

        });

        Button gripperOut = new Button("Gripper open");
        gripperOut.setPrefSize(120, 60);
        gripperOut.setOnAction(gripperMove -> {
            route.add("Brood neerzetten");
            this.routePlanScene(stage, this.route);
        });

        Button gripperIn = new Button("Gripper dicht");
        gripperIn.setPrefSize(120, 60);
        gripperIn.setOnAction(gripperMove -> {
            route.add("Brood oppakken");
            this.routePlanScene(stage, this.route);
        });

        Button startRoute = new Button("Start route");
        startRoute.setPrefSize(100, 60);
        startRoute.setOnAction(gripperMove -> {

        });

        Button storeRouteButton = new Button("route opslaan");
        storeRouteButton.setPrefSize(120, 60);
        storeRouteButton.setOnAction(storeRoute -> {

        });

        Button backSpaceButton = new Button("Terug");
        backSpaceButton.setPrefSize(100, 60);
        backSpaceButton.setOnAction(backSpace -> {
            this.backSpace(stage);
        });

        Button routePlanSceneButton = new Button("Route plannen");
        routePlanSceneButton.setMinSize(150, 50);
        routePlanSceneButton.setOnAction(swithcingScene -> {
            this.routePlanScene(stage, this.route);
        });

        Button controlSceneButton = new Button("Besturen");
        controlSceneButton.setMinSize(150, 50);
        controlSceneButton.setOnAction(swithcingScene -> {
            this.controlScene(stage);
        });

        displayRoute(routePane);

        //pane.add(pane1, 10, 10);

        pane.add(forward, 10, 52);
        pane.add(left, 7, 55);
        pane.add(right, 13, 55);
        pane.add(backward, 10, 58);
        pane.add(neutral, 10, 55);
        pane.add(emergencyStop, 20, 55);
        pane.add(gripperIn, 14, 55);
        pane.add(gripperOut, 14, 58);
        pane.add(startRoute, 4, 55);
        pane.add(storeRouteButton, 5, 55);
        pane.add(routePlanSceneButton, 1, 0);
        pane.add(controlSceneButton, 0, 0);
        pane.add(backSpaceButton, 6, 55);
        pane.add(routePane, 0, 25, 30, 20);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    //Deletes latest manouvre in the route that is being made
    public void backSpace(Stage stage){
        this.route.remove(this.route.size() - 1);
        this.routePlanScene(stage, this.route);
    }

    //Gui layout for the boebot control page
    public void controlScene(Stage stage){
        GridPane pane = new GridPane();
        //BorderPane borderPane = new BorderPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Button forward = new Button("↑" );
        forward.setMinSize(60, 60);
        forward.setPrefSize(60, 60);
        forward.setOnAction(movingForward -> {
            commands.forwardbutton();
        });

        Button left = new Button("←" );
        forward.setMinSize(60, 60);
        left.setPrefSize(60, 60);
        left.setOnAction(movingLeft -> {
            commands.leftButton();
        });

        Button right = new Button("→");
        forward.setMinSize(60, 60);
        right.setPrefSize(60, 60);
        right.setOnAction(movingRight -> {
            commands.rightButton();
        });

        Button backward = new Button("↓");
        forward.setMinSize(60, 60);
        backward.setPrefSize(60, 60);
        backward.setOnAction(movingBackward -> {
            commands.backwardButton();
        });

        Button neutral = new Button("o");
        forward.setMinSize(60, 60);
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
        gripper.setPrefSize(120, 60);
        gripper.setOnAction(gripperMove -> {
            commands.gripperButton();
        });

        Button routePlanSceneButton = new Button("Route plannen");
        routePlanSceneButton.setPrefSize(150, 50);
        routePlanSceneButton.setOnAction(swithcingScene -> {
            this.routePlanScene(stage, new ArrayList<String>());
        });

        Button controlSceneButton = new Button("Besturen");
        controlSceneButton.setPrefSize(150, 50);
        controlSceneButton.setOnAction(swithcingScene -> {
            this.controlScene(stage);
        });

        pane.add(forward, 10, 52);
        pane.add(left, 7, 55);
        pane.add(right, 13, 55);
        pane.add(backward, 10, 58);
        pane.add(neutral, 10, 55);
        pane.add(emergencyStop, 20, 55);
        pane.add(gripper, 14, 55);
        pane.add(routePlanSceneButton, 1, 0);
        pane.add(controlSceneButton, 0, 0);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}


