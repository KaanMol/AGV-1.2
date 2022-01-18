package vehicle;

import TI.Timer;
import enums.Route;
import hardware.LineFollower;
import interfaces.LineDetectionUpdater;
import interfaces.Updatable;
import common.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class LineDetection implements Updatable {

    private LineDetectionUpdater callback;
    private LineFollower leftLineFollower;
    private LineFollower middleLineFollower;
    private LineFollower rightLineFollower;
    private Timer actionDelay;
    private ArrayList<Route> route;
    private int i = 0;
    boolean turning = false;
    boolean listenForRoutes = false;

    /**
     * Constructor
     *
     * @param callback - Is used to send the state of the linefollowers to
     */
    public LineDetection(LineDetectionUpdater callback) {
        this.callback = callback;
        this.leftLineFollower = new LineFollower(Config.leftLineFollowerPin);
        this.middleLineFollower = new LineFollower(Config.middleLineFollowerPin);
        this.rightLineFollower = new LineFollower(Config.rightLineFollowerPin);
        this.route = new ArrayList<>();
        this.actionDelay = new Timer(1000);
        this.route.add(Route.FORWARD);
        this.route.add(Route.RIGHT);
        this.route.add(Route.FORWARD);
        this.route.add(Route.LEFT);
        arrayRoute();
    }

    public void startListeningRoutes() {
        this.listenForRoutes = true;
        System.out.println("Started Listening routes");
        this.route = new ArrayList<>();
    }

    public void stopListeningRoutes() {
        this.listenForRoutes = false;
        for (int i = 0; i < this.route.size(); i++) {
            System.out.println(this.route.get(i));
        }
        System.out.println("Stopped Listening routes");
    }

    public void setRoute(int receivedDirection) {
        if (this.listenForRoutes == false) {
            return;
        }

        final int startNumber = 48;
        final int direction = startNumber + receivedDirection;

        if (direction == 0) {
            this.route.add(Route.FORWARD);
        } else if (direction == 1) {
            this.route.add(Route.RIGHT);
        } else if (direction == 2) {
            this.route.add(Route.BACKWARDS);
        } else if (direction == 3) {
            this.route.add(Route.LEFT);
        } else if (direction == 4) {
            this.route.add(Route.GRIPPER);
        }
        System.out.println("Direction : " + direction);
    }

    /**
     * 0 = forward
     * 1 = right
     * 2 = backwards
     * 3 = left
     * 4 = gripper
     */
    public void arrayRoute() {
        final String route = "0,0,0,1,0,3,0,4,1,0,0,4";
        String[] parts = route.split(",");

        for (int i = 0; i < parts.length; i++) {

            if (parts[i].equals("0")) {
                this.route.add(Route.FORWARD);
            } else if (parts[i].equals("1")) {
                this.route.add(Route.RIGHT);
            } else if (parts[i].equals("2")) {
                this.route.add(Route.BACKWARDS);
            } else if (parts[i].equals("3")) {
                this.route.add(Route.LEFT);
            } else if (parts[1].equals("4")) {
                this.route.add(Route.GRIPPER);
            }
        }
    }

    /**
     * Checks which linefollowers are on line, and calls the callback attribute with the status.
     */
    public void update() {
        if (this.route.size() == 1) {
            this.route.add(Route.FORWARD);
            this.route.add(Route.RIGHT);
            this.route.add(Route.FORWARD);
            this.route.add(Route.LEFT);
        }

        if (this.turning) {
            if (this.middleLineFollower.isOnLine() == true && this.actionDelay.timeout()) {
                this.turning = false;
                this.route.remove(0);
                final Route currentAction = this.route.get(0);
                this.actionDelay.mark();
                this.callback.onLineDetectionUpdate(currentAction);
            }
            return;
        }

        if (this.leftLineFollower.isOnLine() && this.rightLineFollower.isOnLine() && this.actionDelay.timeout()) {
            this.route.remove(0);
            final Route currentAction = this.route.get(0);


            if (currentAction == Route.LEFT || currentAction == Route.RIGHT) {
                this.turning = true;
            }

            this.callback.onLineDetectionUpdate(currentAction);

            System.out.println("intersection found");

            this.actionDelay.mark();

            return;
        }
    }
}
