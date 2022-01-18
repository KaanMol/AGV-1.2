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
    boolean detected = false;
    boolean turning = false;

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
        this.actionDelay = new Timer(500);
        this.route.add(Route.FORWARD);
        this.route.add(Route.RIGHT);
        this.route.add(Route.FORWARD);
        this.route.add(Route.LEFT);
        arrayRoute();
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
//        if (this.turning && (this.leftLineFollower.isOnLine() == false && this.rightLineFollower.isOnLine() == false)) {
//            this.turning = false;
//            this.callback.onLineDetectionUpdate(Route.FORWARD);
//            return;
//        }

        if (this.leftLineFollower.isOnLine() && this.rightLineFollower.isOnLine() && this.actionDelay.timeout()) {
//            this.detected = true;
            if (this.middleLineFollower.isOnLine() == false && this.turning) {
                return;
            }

            if (this.middleLineFollower.isOnLine()) {
                this.turning = false;
            }

            final Route currentAction = this.route.get(0);
            this.callback.onLineDetectionUpdate(currentAction);

            if (currentAction == Route.LEFT || currentAction == Route.RIGHT) {
               this.turning = true;
            }


            this.route.remove(0);
            System.out.println("intersection found");
            this.actionDelay.mark();

            if (this.route.size() == 0) {
                this.route.add(Route.FORWARD);
                this.route.add(Route.RIGHT);
                this.route.add(Route.FORWARD);
                this.route.add(Route.LEFT);
            }

            return;

//            this.route.remove(0);
        }

//        this.callback.onLineDetectionUpdate(Route.FORWARD);

//        this.middleLineFollower.isOnLine()



//        if (this.detected == true ) {
//            final Route currentAction = ;
////
//
//
//            this.detected = false;
//            this.actionDelay.mark();
//        }





//        if (this.leftLineFollower.isOnLine()
//                && rightLineFollower.isOnLine()
//                && middleLineFollower.isOnLine()) {
//            if (this.route.get(i).equals(Route.FORWARD)) {
//            }
//
//
//        } else if (middleLineFollower.isOnLine()
//                && rightLineFollower.isOnLine()) {
//            this.callback.onLineDetectionUpdate(Route.ALL);
//        } else if (middleLineFollower.isOnLine()
//                && leftLineFollower.isOnLine()) {
//            this.callback.onLineDetectionUpdate(Route.ALL);
//
//        } else if (middleLineFollower.isOnLine() == false
//                && leftLineFollower.isOnLine() == false
//                && rightLineFollower.isOnLine() == false) {
//            this.callback.onLineDetectionUpdate(Route.STOP);
//        } else if (this.leftLineFollower.isOnLine() == true
//                && this.rightLineFollower.isOnLine() == false) {
//            this.callback.onLineDetectionUpdate(Route.LEFT);
//        } else if (!leftLineFollower.isOnLine() == false
//                && this.rightLineFollower.isOnLine() == true) {
//            this.callback.onLineDetectionUpdate(Route.RIGHT);
//        } else {
//            this.callback.onLineDetectionUpdate(Route.FORWARD);
//        }

    }
}
