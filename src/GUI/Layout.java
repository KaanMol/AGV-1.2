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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Layout extends Application {
    private Commands commands;
    private ArrayList<String> route;
    private ArrayList<String> arrowRoute;
    private RoutePlanScene routePlanScene;
    private ControlScene controlScene;
    private int currentX;
    private int currentY;
    private Direction upcomingDirection;


    @Override
    public void start(Stage stage) {
        this.route = new ArrayList<String>();
        this.arrowRoute = new ArrayList<String>();
        this.initializeArrowRoute();

        this.commands = new Commands();
        this.routePlanScene = new RoutePlanScene();
        this.controlScene = new ControlScene();

        this.currentX = 5;
        this.currentY = 5;
        this.upcomingDirection = Direction.FORWARD;

        this.controlScene.controlScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
    }

    public void initializeArrowRoute(){
        for(int i = 0; i < 100; i++){
            this.arrowRoute.add("\t");
        }
    }

    public static void main() {
        launch(Layout.class);
    }

}


