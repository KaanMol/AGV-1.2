package vehicle;

import TI.Timer;
import enums.Route;
import hardware.LineFollower;
import interfaces.LineDetectionUpdater;
import interfaces.Updatable;
import common.Config;

import java.util.*;

public class LineDetection implements Updatable {

    private LineDetectionUpdater callback;
    private LineFollower leftLineFollower;
    private LineFollower middleLineFollower;
    private LineFollower rightLineFollower;
    private Gripper gripper;
    private Timer actionDelay;
    private ArrayList<Route> route;
    private int i = 0;
    boolean turning = false;
    boolean isGrippering = false;
    boolean listenForRoutes = false;
    boolean isEnabled = true;

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
        //this.gripper = new Gripper();
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
        this.route.clear();
    }

    public void stopListeningRoutes() {
        this.listenForRoutes = false;
        this.turning = false;
        for (int i = 0; i < this.route.size(); i++) {
            System.out.println(i);
            System.out.println(this.route.get(i).name());
        }

        System.out.println("Stopped Listening routes");
        System.out.println(this.route.size());
    }

    public void setRoute(int receivedDirection) {

        System.out.println("HI");
        if (this.listenForRoutes == false) {
            return;
        }

        if (receivedDirection == 0) {
            this.route.add(Route.FORWARD);
        } else if (receivedDirection == 1) {
            this.route.add(Route.RIGHT);
        } else if (receivedDirection == 2) {
            this.route.add(Route.BACKWARDS);
        } else if (receivedDirection == 3) {
            this.route.add(Route.LEFT);
        } else if (receivedDirection == 4) {
            this.route.add(Route.GRIPPER);
        } else if (receivedDirection == 5) {
            this.route.add(Route.GRIPPERDROP);
        }
//        System.out.println("Direction : " + this.route.get(this.route.size() - 1).name());
    }

    /**
     * 0 = forward
     * 1 = right
     * 2 = backwards
     * 3 = left
     * 4 = gripper pickup
     * 5 = gripper drop
     */
    public void arrayRoute() {
        final String route = "0,0,4,1,0,3,0,4,1,0,0,4";
        String[] parts = route.split(",");

        for (int i = 0; i < parts.length; i++) {

            if (parts[i].equals("0")) {
                this.route.add(Route.FORWARD);
            } else if (parts[i].equals("1")) {
                this.route.add(Route.RIGHT);
                //this.route.add(Route.FORWARD);
            } else if (parts[i].equals("2")) {
                this.route.add(Route.BACKWARDS);
            } else if (parts[i].equals("3")) {
                this.route.add(Route.LEFT);
                //this.route.add(Route.FORWARD);
            } else if (parts[i].equals("4")) {
                this.route.add(Route.GRIPPER);
            } else if (parts[i].equals("5")) {
                this.route.add(Route.GRIPPERDROP);
            }
        }
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    /**
     * Checks which linefollowers are on line, and calls the callback attribute with the status.
     */
    public void update() {

        if (this.listenForRoutes == true || this.isEnabled == false) {
            return;
        }

        try {
//            if (this.route.size() == 1) {
//                this.route.add(Route.FORWARD);
//                this.route.add(Route.RIGHT);
//                this.route.add(Route.FORWARD);
//                this.route.add(Route.LEFT);
//            }

            if (this.turning) {
                if (this.middleLineFollower.isOnLine() == true && this.actionDelay.timeout()) {
                    this.turning = false;
                    final Route currentAction = this.route.get(0);
                    this.route.remove(0);
//                    System.out.println(currentAction);
                    this.actionDelay.mark();
                    this.callback.onLineDetectionUpdate(currentAction);
                }
                return;
            }

//            if(this.isGrippering) {
//                this.callback.onLineDetectionUpdate(Route.GRIPPER);
//                return;
//            }


            if (this.leftLineFollower.isOnLine() && this.rightLineFollower.isOnLine() && this.actionDelay.timeout()) {
                final Route currentAction = this.route.get(0);
                this.route.remove(0);
//                System.out.println(currentAction);

                if (currentAction == Route.LEFT || currentAction == Route.RIGHT) {
                    this.turning = true;
                }

                this.callback.onLineDetectionUpdate(currentAction);


                System.out.println("intersection found");
                this.actionDelay.mark();


                return;
            }
        } catch (Exception e) {
//            this.route.add(Route.FORWARD);
//            this.route.add(Route.RIGHT);
//            this.route.add(Route.FORWARD);
//            this.route.add(Route.LEFT);
//            System.out.println(e);
        }
    }
}
