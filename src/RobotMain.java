//import GUI.Layout;
import TI.BoeBot;
import TI.Timer;
import common.Config;
import common.WirelessConfig;
import enums.*;
import hardware.Button;
import interfaces.*;
import vehicle.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RobotMain implements MovementUpdater, CollisionDetectionUpdater, WirelessUpdater, InfraredUpdater, LineDetectionUpdater, DistanceDetectionUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private Remote remote;
    private DrivingNotification drivingNotification;
    private CollisionDetection collisionDetection;
    private WirelessConnection wirelessConnection;
    private LineDetection lineDetection;
    private Button emergencyStop;
    private boolean emergencyStopActivated = false;
    private boolean hasObstacle = false;
    private Direction lastHeading = Direction.FORWARD;
    private DrivingLights drivinglights;
    private Gripper gripper;
    private DistanceDetection distanceDetection;
    private Button startButton;
    private boolean bottomSensorActive = true;
    private boolean obstacleExpected = false;
    private boolean obstaclePicked = false;
    private Timer pickUpTimer = new Timer(1000);

    ControlOwner controlOwner = ControlOwner.Line;

    //For the gui
    //private Layout layout;

    public static void main(String[] args) {
        new RobotMain();
    }

    /**
     * Constructor
     */
    public RobotMain() {

        this.initialize();
        this.updater();
    }

    /**
     * Initializes all systems and adds them to the queue
     */
    private void initialize() {
        this.processes = new ArrayList<>();

        this.movement = new Movement(this);
        this.processes.add(this.movement);

        this.collisionDetection = new CollisionDetection(this);
        this.processes.add(this.collisionDetection);

        this.lineDetection = new LineDetection(this);
        this.processes.add(this.lineDetection);

        this.drivingNotification = new DrivingNotification();
        this.processes.add(this.drivingNotification);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);

        this.remote = new Remote(this);
        this.processes.add(this.remote);

        this.drivinglights = new DrivingLights();

        this.wirelessConnection = new WirelessConnection(this);
        this.processes.add(this.wirelessConnection);

        this.gripper = new Gripper(this.movement);
        this.processes.add(this.gripper);

        this.distanceDetection = new DistanceDetection(this);
        this.processes.add(this.distanceDetection);

        this.emergencyStop = new Button(Config.emergencyStopButtonPin);

        this.startButton = new Button(0);

        this.gripper.getGripper().getGripper().update(1500);
    }

    /**
     * Handles the updates of the system by calling the update method from Updatable interface
     */
    private void updater() {
        while (true) {
            BoeBot.wait(1);
            if (this.emergencyStopActivated == false) {
                for (Updatable process : processes) {
                    if (this.emergencyStop.isPressed()) {
                        System.out.println("stop");
                        this.movement.neutral();
                        this.drivingNotification.stop();
                        this.emergencyStopActivated = true;
                        break;
                    }
                    if (this.emergencyStopActivated) {
                        this.movement.neutral();
                    }
                    process.update();
                }
            } else if (this.startButton.isPressed()) {
                this.movement.forward();
                this.emergencyStopActivated = false;
            }
        }
    }

    /**
     * Stops all systems in the case of emergency
     */
    private void stop() {
        this.movement.neutral();
        this.drivingNotification.stop();
        this.blinkers.stop();
    }


    /**
     * This method is called when the distance from the ultrasonic sensor is too little
     */
//    public void onDistanceDetectionUpdate(boolean hasObstacle){
//        if(hasObstacle) {
//            this.movement.neutral();
//        }
//    }

    public void onDistanceDetectionUpdate(HashMap<Ultrasonic, Double> hasObstacle) {


//        if (this.gripper.gripperStatus() && obstaclePicked) {
//            this.movement.forward();
//            obstaclePicked = false;
//        }
        if (!this.obstaclePicked && obstacleExpected && hasObstacle.get(Ultrasonic.BOTTOM)<4) {
            System.out.println("gripper toggle");
            //this.movement.pause();
            bottomSensorActive = false;
            this.movement.neutral();
            gripper.toggle();
            pickUpTimer.mark();
            obstacleExpected = false;
            obstaclePicked = true;
        }
        if(obstaclePicked && obstacleExpected){
            System.out.println("gripper toggle");
            bottomSensorActive = true;
            //this.gripper.getGripper().getGripper().update(1300);
            this.gripper.toggle();
            pickUpTimer.mark();
            this.obstaclePicked = false;
            this.obstacleExpected = false;
            this.movement.neutral();
        }

//        else if (this.bottomSensorActive) {
//            if (hasObstacle.get(Ultrasonic.BOTTOM) < 4) {
//                this.movement.neutral();
//            }
//        } else if (hasObstacle.get(Ultrasonic.TOP) < 4) {
//            this.movement.neutral();
//        }
        //System.out.println(hasObstacle.get(Ultrasonic.BOTTOM) + "bottom");
        //System.out.println(hasObstacle.get(Ultrasonic.TOP) + "top");

        if (hasObstacle.get(Ultrasonic.TOP) <= 10) {
            this.movement.pause();
            System.out.println("STOP");
            this.hasObstacle = true;
        }


        if (this.hasObstacle == true
                && hasObstacle.get(Ultrasonic.BOTTOM) > 8
                && hasObstacle.get(Ultrasonic.TOP) > 10) {
            System.out.println("START");
            this.hasObstacle = false;
            this.movement.play();
        }
    }



    /**
     * This method moves the robot towards the side the user told it to with the remote
     */
    public void onInfraredCommandUpdate(int signal) {
        if (this.controlOwner != ControlOwner.Remote) {
            System.out.println("Remote took control!");
            this.controlOwner = ControlOwner.Remote;
            this.lineDetection.setEnabled(false);
        }
//        System.out.println(signal);

        if (signal == Config.remoteForward) {
            this.movement.forward();
        } else if (signal == Config.remoteBackward) {
            this.movement.backward();
        } else if (signal == Config.remoteRight) {
            this.movement.turnRight();
        } else if (signal == Config.remoteLeft) {
            this.movement.turnLeft();
        } else if (signal == Config.remoteNeutral) {
            this.movement.brake();
        } else if (signal == Config.remoteEmergencyStop) {
            this.emergencyStopActivated = true;
        } else if (signal == Config.remoteControlTransfer) {
            System.out.println("Linefollower was given control!");
            this.lineDetection.setEnabled(true);
            this.controlOwner = ControlOwner.Line;
        } else if (signal == Config.remoteGripper) {
            this.gripper.toggle();
        }
    }

    /**
     * Callback that gets called when the vehicle direction changes.
     *
     * @param heading - The direction where the vehicle is heading to
     */
    public void onMovementUpdate(Direction heading) {
        if (heading == Direction.LEFT || heading == Direction.RIGHT) {
            this.blinkers.start(heading);
            this.drivingNotification.stop();

        } else if (heading == Direction.FORWARD || heading == Direction.BACKWARD || heading == Direction.NEUTRAL) {
            this.blinkers.stop();
            this.drivinglights.start(heading);
            if (heading == Direction.BACKWARD) {
                this.drivingNotification.start();
            } else {
                this.drivingNotification.stop();
            }

        } else {
            this.blinkers.stop();
        }
    }

    /**
     * Callback that gets called when the vehicle detects a collision.
     *
     * @param whiskerCollision - Which whiskers are triggered.
     */
    public void onCollisionDetectionUpdate(WhiskerStatus whiskerCollision) {
        switch (whiskerCollision) {
            case LEFT:
            case BOTH:
                this.movement.setManoeuvre(Manoeuvre.LEFT);
                break;
            case RIGHT:
                this.movement.setManoeuvre(Manoeuvre.RIGHT);
                break;
        }
    }

    /**
     * Callbakc that gets called when the vehicle detects Bluetooth
     * @param command Which ASCII Number is given
     */
    public void onWirelessUpdate(int command) {
        System.out.println("bluetooth method triggered");
        if (this.controlOwner != ControlOwner.Wireless) {
            System.out.println("Wireless took control!");
            this.lineDetection.setEnabled(false);
            this.controlOwner = ControlOwner.Wireless;
        }


        System.out.println("Looking for command");
        if (command == WirelessConfig.routeTransmissionStart) {
            this.lineDetection.startListeningRoutes();
            this.movement.neutral();
        } else if (command == WirelessConfig.routeTransmissionEnd) {
            this.lineDetection.stopListeningRoutes();
            this.lineDetection.setEnabled(true);
            this.controlOwner = ControlOwner.Line;
            this.movement.forward();
        } else if (command >= 0 && command <= 4) {
            System.out.println("ENTER");
            this.lineDetection.setRoute(command);
        } else if (command == WirelessConfig.backward) {
            this.movement.backward();
        } else if (command == WirelessConfig.left) {
            this.movement.turnLeft();
        } else if (command == WirelessConfig.right) {
            this.movement.turnRight();
        } else if (command == WirelessConfig.forward) {
            System.out.println("forward command received");
            this.movement.forward();
        } else if (command == WirelessConfig.stop) {
            this.movement.neutral();
        } else if (command == WirelessConfig.transfer) {
            System.out.println("Linefollower was given control!");
            this.lineDetection.setEnabled(true);
            this.controlOwner = ControlOwner.Line;
        } else if (command == WirelessConfig.gripper) {
            this.gripper.toggle();
        }
    }



    /**
     * Callback that gets called when the vehicle detects a line.
     * @param lineDetection
     */
    public void onLineDetectionUpdate(Route lineDetection) {
        if (this.movement.getHeading() == Direction.NEUTRAL) {
            return;
        }

        if (this.controlOwner != ControlOwner.Line) {
            return;
        }

        this.obstacleExpected = false;
        switch (lineDetection) {
            case FORWARD:
                this.movement.forward();
                break;
            case LEFT:
                System.out.println("GO LEFT");
                this.movement.turnLeft();
                break;
            case RIGHT:
                System.out.println("GO RIGHT");
                this.movement.turnRight();
                break;
            case STOP:
                this.movement.neutral();
                this.drivingNotification.start();
                this.drivinglights.lineLights();
                break;
            case ALL:
                this.movement.neutral();
                break;
            case GRIPPER:
                //this.obstacleExpected = true;
                //this.movement.forward();

                //this.movement.br();
                //this.gripper.toggle();
                this.obstacleExpected = true;
                System.out.println("obstacle on");

                //this.obstaclePicked = true;
                break;
        }
                //BoeBot.wait(100000);
//            case GRIPPERDROP:
//                System.out.println("gripper toggle");
//                bottomSensorActive = true;
//                this.gripper.getGripper().getGripper().update(1300);
//                pickUpTimer.mark();
//                obstaclePicked = false;
//                this.movement.neutral();
//                break;
    }

    public void emergencyStop() {
        if(this.emergencyStop.isPressed()){
            this.movement.neutral();
            while(this.emergencyStop.isPressed()){
            }
        }

    }
}
