package GUI;

import javafx.geometry.Insets;
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
    private ArrayList<String> arrowRoute;
    private int currentX;
    private int currentY;
    private Direction upcomingDirection;
    private ArrayList<String> arrows;



    /**
     * Displays the page where the user can customize a new route and save it to the boebot
     *
     * @param route - Arraylist with the directions the boebot has to follow
     */
    public void routePlanScene(Stage stage, ArrayList<String> route, ArrayList<String> arrowRoute, int currentX, int currentY, Direction upcomingDirection){
        this.controlScene = new ControlScene();
        this.route = route;
        this.arrowRoute = arrowRoute;
        this.currentX = currentX;
        this.currentY = currentY;
        this.upcomingDirection = upcomingDirection;
        this.arrows = new ArrayList<>(Arrays.asList("←", "↑", "→"));
        GridPane pane = new GridPane();
        GridPane routePane = new GridPane();
        GridPane routeGridPane = new GridPane();
        routePane.setHgap(30);
        pane.setHgap(10);
        pane.setVgap(10);

        try{
            this.image(pane);
        }
        catch(IOException exception){

        }

        //With these buttons the user can add a manouvre to the route
        Button forward = new Button("↑" );
        forward.setPrefSize(60, 60);
        forward.setOnAction(movingForward -> {
            route.add("Vooruit ↑");
            int index = ((this.currentY - 1) * 10 + this.currentX) - 1;
            String newArrow = "";
            switch (this.upcomingDirection){
                case FORWARD:
                    newArrow = "↑";
                    this.currentY -= 1;
                    break;
                case LEFT:
                    newArrow = "←";
                    this.currentX -= 1;
                    break;
                case RIGHT:
                    newArrow = "→";
                    this.currentX += 1;
                    break;
                case BACKWARD:
                    newArrow = "↓";
                    this.currentY += 1;
                    break;
            }
            this.arrowRoute.set(index, newArrow);
            this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        Button left = new Button("←" );
        left.setPrefSize(60, 60);
        left.setOnAction(movingLeft -> {
            route.add("Links ←");
            int index = ((this.currentY - 1) * 10 + this.currentX) - 1;
            //this.upcomingDirection = Direction.FORWARD;
            String newArrow = "";
            if(this.upcomingDirection == Direction.FORWARD){
                newArrow = "←";
            }
            else if(this.upcomingDirection == Direction.LEFT){
                newArrow = "↓";
            }
            else if(this.upcomingDirection == Direction.RIGHT){
                newArrow = "↑";
            }
            else if(this.upcomingDirection == Direction.BACKWARD){
                newArrow = "→";
            }

            this.upcomingDirection = Direction.LEFT;
            this.arrowRoute.set(index, newArrow);
            this.currentX -= 1;
            this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        Button right = new Button("→");
        right.setPrefSize(60, 60);
        right.setOnAction(movingRight -> {
            route.add("Rechts →");
            int index = ((this.currentY - 1) * 10 + this.currentX) - 1;
            //this.upcomingDirection = Direction.FORWARD;
            String newArrow = "";

            if(this.upcomingDirection == Direction.FORWARD){
                newArrow = "→";
            }
            else if(this.upcomingDirection == Direction.LEFT){
                newArrow = "↑";
            }
            else if(this.upcomingDirection == Direction.RIGHT){
                newArrow = "↓";
            }
            else if(this.upcomingDirection == Direction.BACKWARD){
                newArrow = "←";
            }

            this.upcomingDirection = Direction.RIGHT;
            this.arrowRoute.set(index, newArrow);
            this.currentX += 1;
            this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        Button backward = new Button("↓");
        backward.setPrefSize(60, 60);
        backward.setOnAction(movingBackward -> {
            //boebot mag niet achteruit rijden
            route.add("Achteruit ↓");
            this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        Button neutral = new Button("o");
        neutral.setPrefSize(60, 60);
        backward.setOnAction(notMoving -> {
            route.add("Neutraal ");
            this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        Button gripperOut = new Button("Gripper open");
        gripperOut.setPrefSize(120, 60);
        gripperOut.setOnAction(gripperMove -> {
            route.add("Brood neerzetten");
            this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        Button gripperIn = new Button("Gripper dicht");
        gripperIn.setPrefSize(120, 60);
        gripperIn.setOnAction(gripperMove -> {
            route.add("Brood oppakken");
            this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        //Makes the boebot execute the manouvres in the route
        Button startRoute = new Button("Start route");
        startRoute.setPrefSize(100, 60);
        startRoute.setOnAction(gripperMove -> {

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
            this.backSpace(stage);
        });

        //Switches to the page where the user can control the boebot directly
        Button controlSceneButton = new Button("Besturen");
        controlSceneButton.setMinSize(150, 50);
        controlSceneButton.setOnAction(swithcingScene -> {
            this.controlScene.controlScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
        });

        this.displayRoute(routePane);
        this.gridDisplay(routeGridPane);

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
        pane.add(routePane, 2, 25, 30, 20);
        pane.add(routeGridPane, 0, 1, 2, 1);

        //Sets all the buttons to the page to display them
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void gridDisplay(GridPane pane){
        pane.setGridLinesVisible(true);
        int index = 0;
        for(int j = 0; j < 10; j++){
            for(int i = 0; i < 10; i++){
                Label label = new Label(this.arrowRoute.get(index));
                pane.add(label, i, j);
                index++;
            }
        }
    }

    public void image(GridPane pane)throws IOException{
        FileInputStream inputstream = new FileInputStream("src\\GUI\\image\\Barts_Bakkerij_logo.png");
        Image image = new Image(inputstream);
        ImageView view = new ImageView(image);
        pane.add(view, 1, 0);
    }

    public void displayRoute(GridPane pane){
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

    public void backSpace(Stage stage){
        this.route.remove(this.route.size() - 1);
        this.routePlanScene(stage, this.route, this.arrowRoute, this.currentX, this.currentY, this.upcomingDirection);
    }

}
