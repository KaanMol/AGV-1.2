package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private Button brake;
    private Button emergencyStop;
    private boolean isPlanningRoute = false;
    private ArrayList<Integer> route = new ArrayList<>();
    private ListView routeList = new ListView();
    Map<Route, int[]> commandMapping  = new HashMap<Route, int[]>() {{
        put(Route.FORWARD, new int[]{0, 119});
        put(Route.RIGHT, new int[]{1, 100});
        put(Route.BACKWARDS, new int[]{2, 115});
        put(Route.LEFT, new int[]{3, 97});
        put(Route.GRIPPER, new int[]{4, 101});
        //put(Route.GripperDrop, new int[]{5, 102});
        put(Route.STOP, new int[]{5, 113});
        put(Route.Brake, new int[]{6, 98});
    }};

    private Consumer<Route> sendCommand = data -> {
        int[] command = this.commandMapping.get(data);

        if (this.isPlanningRoute) {
            this.route.add(command[0]);
            this.routeList.getItems().add(0, data.name());

            return;
        }
        System.out.println("no route planning");

        try {
            System.out.println(command[1]);
            serialPort.writeInt(command[1]);
            System.out.println("wrote int");
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
            this.brake.setDisable(this.isPlanningRoute);
            this.emergencyStop.setDisable(this.isPlanningRoute);

            this.route.clear();
            this.routeList.getItems().clear();
        });

        this.removeCommand.setOnMouseClicked(event -> {
            if(this.route.size() > 0){
                this.route.remove(this.route.size() - 1);
                this.routeList.getItems().remove(0);
            }
        });

        this.startRoute.setOnMouseClicked(event -> { this.startRoute(); });

        // Control points
        this.emergencyStop = new Button("e");
        gridPane.add(emergencyStop, 4, 3);
        emergencyStop.setOnMouseClicked(event -> sendCommand.accept(Route.STOP));

        this.brake = new Button("b");
        gridPane.add(brake, 1, 3);
        brake.setOnMouseClicked(event -> sendCommand.accept(Route.Brake));

        Button gripper = new Button("g");
        gridPane.add(gripper, 3, 3);
        gripper.setOnMouseClicked(event -> sendCommand.accept(Route.GRIPPER));

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
//            System.out.println(route[0]);
//            System.out.println(route[1]);
//            System.out.println(route[2]);
//            System.out.println(route[3]);
//            System.out.println(route[4]);
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
            launch(Layout.class);
    }
}