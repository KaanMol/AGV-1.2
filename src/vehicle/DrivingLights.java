package vehicle;

import common.Config;
import enums.Direction;
import hardware.Led;

import java.awt.*;

public class DrivingLights {
    private Led topLeft;
    private Led topRight;
    private Led bottomLeft;
    private Led bottomRight;
    private Direction direction;

    public DrivingLights() {
        this.topLeft = new Led(Led.topLeft);
        this.topRight = new Led(Led.topRight);
        this.bottomLeft = new Led(Led.bottomLeft);
        this.bottomRight = new Led(Led.bottomRight);
        this.direction = Direction.NEUTRAL;
        this.off();
    }

    public void start(Direction direction) {
        if (this.direction == direction) {
            return;
        }
        this.direction = direction;
        this.on();
    }

    public void on(){
        if (this.direction == Direction.FORWARD) {
            topRight.setColor(Color.WHITE);
            topLeft.setColor(Color.WHITE);
        }
        else if (this.direction == Direction.BACKWARD) {
            bottomRight.setColor(Color.RED);
            bottomLeft.setColor(Color.RED);
        }
    }

    public void stop() {
        this.direction = Direction.NEUTRAL;
        this.off();
    }

    private void off() {
        this.topLeft.off();
        this.topRight.off();
        this.bottomLeft.off();
        this.bottomRight.off();
    }
}
