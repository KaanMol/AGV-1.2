package GUI;
//
//import common.WirelessConfig;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import vehicle.Movement;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class Layout extends Application {
//    private Commands commands;
//    private ArrayList<String> route;
//    private ArrayList<String> arrowRoute;
//    private RoutePlanScene routePlanScene;
//    private ControlScene controlScene;
//    private int currentX;
//    private int currentY;
//    private Direction upcomingDirection;
//    private ArrayList<Integer> routeCommands;
//
//
//    @Override
//    public void start(Stage stage) {
//        this.route = new ArrayList<String>();
//        this.arrowRoute = new ArrayList<String>();
//        this.routeCommands = new ArrayList<Integer>();
//        this.initializeArrowRoute();
//
//        this.commands = new Commands();
//        this.routePlanScene = new RoutePlanScene();
//        this.controlScene = new ControlScene();
//
//        this.currentX = 5;
//        this.currentY = 5;
//        this.upcomingDirection = Direction.FORWARD;
//
//        this.controlScene.controlScene(stage, this.route, this.routeCommands);
//    }
//
//    public void initializeArrowRoute(){
//        for(int i = 0; i < 100; i++){
//            this.arrowRoute.add("\t");
//        }
//    }
//
//    public static void main() {
//        launch(Layout.class);
//    }
//
//}
//
//

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Layout extends Application {
    private static SerialPort serialPort;
    private TextField textField = new TextField("COM7");
    private Button connectionButton = new Button("Connect");
    private Button disconnectButton = new Button("Disconnect");
    private Button removeCommand = new Button("Commando verwijderen");
    private Button startRoute = new Button("Route starten");
    private boolean isPlanningRoute = false;
    private ArrayList<Integer> route = new ArrayList<>();
    private ListView routeList = new ListView();
    Map<Route, int[]> commandMapping  = new HashMap<Route, int[]>() {{
        put(Route.FORWARD, new int[]{0, 119});
        put(Route.RIGHT, new int[]{1, 100});
        put(Route.BACKWARDS, new int[]{2, 115});
        put(Route.LEFT, new int[]{3, 97});
        put(Route.GRIPPER, new int[]{4, 101});
        put(Route.STOP, new int[]{5, 113});
    }};

    private Consumer<Route> sendCommand = data -> {
        int[] command = this.commandMapping.get(data);

        if (this.isPlanningRoute) {
            this.route.add(command[0]);
            this.routeList.getItems().add(0, data.name());

            return;
        }

        try {
            serialPort.writeInt(command[1]);
        } catch (SerialPortException e){
            e.printStackTrace();
        }

    };

    @Override
    public void start(Stage stage) {
        stage.setTitle("BoeBotController");
        stage.show();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // COM port connection
        HBox comPort = new HBox();
        Label label1 = new Label("COM:");

        gridPane.add(label1, 0, 0);
        gridPane.add(this.textField, 0, 1);

        connectionButton.setOnMouseClicked(event -> this.connect(textField.getText()));
        disconnectButton.setOnMouseClicked(event -> this.disconnect());
        disconnectButton.setDisable(true);

        comPort.getChildren().addAll(label1, this.textField, this.connectionButton, this.disconnectButton);
        gridPane.getChildren().addAll(comPort);

        Text controlType = new Text("Huidige modus: Besturing");
        gridPane.add(controlType, 0, 2);

        Button routePlanner = new Button("Modus overschakelen");
        gridPane.add(routePlanner, 0, 3);

        this.removeCommand.setDisable(true);
        gridPane.add(this.removeCommand, 0, 4);
        this.startRoute.setDisable(true);

        gridPane.add(this.startRoute, 0, 5);
        routePlanner.setOnMouseClicked(event -> {
            this.isPlanningRoute = !this.isPlanningRoute;
            controlType.setText(this.isPlanningRoute ? "Huidige modus: Route inplannen" : "Huidige modus: Besturing");

            this.removeCommand.setDisable(!this.isPlanningRoute);
            this.startRoute.setDisable(!this.isPlanningRoute);

            this.route.clear();
            this.routeList.getItems().clear();
        });

        this.removeCommand.setOnMouseClicked(event -> {
            this.route.remove(this.route.size() - 1);
            this.routeList.getItems().remove(0);
        });

        this.startRoute.setOnMouseClicked(event -> { this.startRoute(); });

        // Control points
        Button stop = new Button("s");
        gridPane.add(stop, 1, 3);
        stop.setOnMouseClicked(event -> sendCommand.accept(Route.STOP));

        Button gripper = new Button("g");
        gridPane.add(gripper, 3, 3);
        stop.setOnMouseClicked(event -> sendCommand.accept(Route.GRIPPER));

        Button backwards = new Button("↓");
        gridPane.add(backwards, 2, 5);
        backwards.setOnMouseClicked(event -> sendCommand.accept(Route.BACKWARDS));

        Button forwards = new Button("↑");
        gridPane.add(forwards, 2, 3);
        forwards.setOnMouseClicked(event -> sendCommand.accept(Route.FORWARD));

        Button left = new Button("→");
        gridPane.add(left, 3, 4);
        left.setOnMouseClicked(event -> sendCommand.accept(Route.RIGHT));

        Button right = new Button("←");
        gridPane.add(right, 1, 4);
        right.setOnMouseClicked(event -> sendCommand.accept(Route.LEFT));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(gridPane, this.routeList);

        Scene scene = new Scene(hBox);
        stage.setScene(scene);
        stage.show();

    }

    private void startRoute() {
        ArrayList<Integer> routeClone = (ArrayList<Integer>) this.route.clone();
        routeClone.add(0, 82);
        routeClone.add(59);
        int[] route = routeClone.stream().mapToInt(i -> i).toArray();

        try {
            this.serialPort.writeIntArray(route);
        } catch (SerialPortException e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Er is een onbekende fout opgetreden.");
            alert.show();
        }

    }

    // Bluetooth connection
    private boolean connect(String port) {
        try {
            this.serialPort = new SerialPort(port);
            this.serialPort.openPort();
            System.out.println(port);
            this.serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            System.out.println("lam");


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Verbonden!");
            alert.show();

            this.connectionButton.setDisable(true);
            this.disconnectButton.setDisable(false);
            this.textField.setDisable(true);

        } catch (SerialPortException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Verbinding kon niet gemaakt worden, verifiëer de COM Port!");
            alert.show();
            System.out.println(e);
        }
        return false;
    }

    // Bluetooth disconnect
    private void disconnect() {
        try {
            this.serialPort.closePort();
            System.out.println(this.connectionButton);
            this.connectionButton.setDisable(false);
            this.disconnectButton.setDisable(true);
            this.textField.setDisable(false);
        } catch (SerialPortException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

//        try {
//            serialPort.openPort(); // Open the serial connection

            launch(Layout.class);
//            serialPort.writeString("Hello student!");
//            byte[] buffer = serialPort.readBytes(10); // Fixed buffer length
//            for (int i = 0; i < 10; i++)
//                System.out.print(buffer[i] + "-");
//            serialPort.closePort();
//        } catch (SerialPortException e) {
//            e.printStackTrace();
//        }

    }
}