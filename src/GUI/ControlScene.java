package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ControlScene {
    private RoutePlanScene routePlanScene;
    private Commands commands;
    private ArrayList<String> route;
    private ArrayList<String> arrowRoute;
    private int currentX;
    private int currentY;
    private Direction upcomingDirection;
    private ArrayList<Integer> routeCommands;

    public void controlScene(Stage stage, ArrayList<String> route, ArrayList<Integer> routeCommands){
        this.routePlanScene = new RoutePlanScene();
        this.commands = new Commands();
        this.route = route;
        this.routeCommands = routeCommands;
        this.arrowRoute = arrowRoute;
        this.currentX = currentX;
        this.currentY = currentY;
        this.upcomingDirection = upcomingDirection;
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        try{
            this.image(pane);
        }
        catch(IOException exception){

        }

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

//        Button backward = new Button("↓");
//        forward.setMinSize(60, 60);
//        backward.setPrefSize(60, 60);
//        backward.setOnAction(movingBackward -> {
//            commands.backwardButton();
//        });

        Button neutral = new Button("o");
        forward.setMinSize(60, 60);
        neutral.setPrefSize(60, 60);
        neutral.setOnAction(notMoving -> {
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
            //this.routePlanScene(stage, new ArrayList<String>());
            this.routePlanScene.routePlanScene(stage, this.route, this.routeCommands);
        });

        pane.add(forward, 10, 52);
        pane.add(left, 7, 55);
        pane.add(right, 13, 55);
        //pane.add(backward, 10, 58);
        pane.add(neutral, 10, 55);
        pane.add(emergencyStop, 20, 55);
        pane.add(gripper, 14, 55);
        pane.add(routePlanSceneButton, 0, 0);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void image(GridPane pane)throws IOException{
        FileInputStream inputstream = new FileInputStream("src\\GUI\\image\\Barts_Bakkerij_logo.png");
        Image image = new Image(inputstream);
        ImageView view = new ImageView(image);
        pane.add(view, 1, 0);
    }
}
