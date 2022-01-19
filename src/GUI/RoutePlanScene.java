package GUI;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RoutePlanScene {
    private ControlScene controlScene;
    private ArrayList<String> route;
    private ArrayList<Integer> routeCommands;
    private Direction previousDirection;
    private GridPane pane;
    private GridPane routePane;
    private RoutePlanCommands commands;
    private Commands bluetoothCommands;

    /**
     * Displays the page where the user can customize a new route and save it to the boebot
     *
     * @param route - Arraylist with the directions the boebot has to follow
     */
    public void routePlanScene(Stage stage, ArrayList<String> route, ArrayList<Integer> routeCommands){
        //this.commands = new Commands();
        this.commands = new RoutePlanCommands();
        this.controlScene = new ControlScene();
        this.routeCommands = routeCommands;
        this.route = route;
        this.routeCommands = routeCommands;
        this.pane = new GridPane();
        this.routePane = new GridPane();
        routePane.setHgap(30);
        pane.setHgap(10);
        pane.setVgap(10);

        try{
            this.commands.image(pane);
        }
        catch(IOException exception){

        }

        //With these buttons the user can add a manouvre to the route
        Button forward = new Button("↑" );
        forward.setPrefSize(60, 60);
        forward.setOnAction(movingForward -> {
            this.route.add("Vooruit ↑");
            this.routeCommands.add(0);

            this.commands.displayRoute(this.routePane, this.route);
        });

        Button left = new Button("←" );
        left.setPrefSize(60, 60);
        left.setOnAction(movingLeft -> {
            this.route.add("Links ←");
            this.routeCommands.add(3);


            this.commands.displayRoute(routePane, this.route);


        });

        Button right = new Button("→");
        right.setPrefSize(60, 60);
        right.setOnAction(movingRight -> {
            this.route.add("Rechts →");
            this.routeCommands.add(1);

            this.commands.displayRoute(routePane, this.route);
        });

        Button backward = new Button("↓");
        backward.setPrefSize(60, 60);
        backward.setOnAction(movingBackward -> {
            //boebot mag niet achteruit rijden
            this.route.add("Achteruit ↓");
            this.routeCommands.add(2);
            this.commands.displayRoute(routePane, this.route);

        });

        Button neutral = new Button("o");
        neutral.setPrefSize(60, 60);
        backward.setOnAction(notMoving -> {
            route.add("Neutraal ");

            this.commands.displayRoute(routePane, this.route);
        });

        Button gripperOut = new Button("Gripper open");
        gripperOut.setPrefSize(120, 60);
        gripperOut.setOnAction(gripperMove -> {
            this.route.add("Brood neerzetten");
            this.routeCommands.add(4);

            this.commands.displayRoute(routePane, this.route);
        });

        Button gripperIn = new Button("Gripper dicht");
        gripperIn.setPrefSize(120, 60);
        gripperIn.setOnAction(gripperMove -> {
            this.route.add("Brood oppakken");
            this.routeCommands.add(4);

            this.commands.displayRoute(routePane, this.route);
        });

        //Makes the boebot execute the manouvres in the route
        Button startRoute = new Button("Start route");
        startRoute.setPrefSize(100, 60);
        startRoute.setOnAction(gripperMove -> {
            this.bluetoothCommands.sendroute(this.routeCommands);
        });

        //Saves the route to the boebot
        Button storeRouteButton = new Button("Route opslaan");
        storeRouteButton.setPrefSize(120, 60);
        storeRouteButton.setOnAction(storeRoute -> {

        });

        //Deletes the last added manouvre from the route
        Button backSpaceButton = new Button("Terug");
        backSpaceButton.setPrefSize(100, 60);
        backSpaceButton.setOnAction(backSpace -> {
            this.commands.backSpace(stage, this.route, this.routeCommands);
        });

        //Switches to the page where the user can control the boebot directly
        Button controlSceneButton = new Button("Besturen");
        controlSceneButton.setMinSize(150, 50);
        controlSceneButton.setOnAction(swithcingScene -> {
            this.controlScene.controlScene(stage, this.route, this.routeCommands);
        });

        this.commands.displayRoute(routePane, this.route);

        pane.add(forward, 10, 52);
        pane.add(left, 7, 55);
        pane.add(right, 13, 55);
        pane.add(backward, 10, 58);
        pane.add(neutral, 10, 55);
        pane.add(gripperIn, 14, 55);
        pane.add(gripperOut, 14, 58);
        pane.add(startRoute, 4, 55);
        pane.add(storeRouteButton, 5, 55);
        pane.add(controlSceneButton, 0, 0);
        pane.add(backSpaceButton, 6, 55);
        pane.add(this.routePane, 0, 25, 30, 20);

        this.bluetoothCommands.openPort();

        //Sets all the buttons to the page to display them
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
