package test;

import test.facade.FakeMovement;

import java.util.ArrayList;

public class MovementTest {

    private FakeMovement movement;

    public MovementTest() {
        this.movement = new FakeMovement();
    }

    public void run() {
        this.movement.forward();
        this.movement.forward();
        this.movement.forward();
        this.movement.turnLeft();
        this.movement.turnRight();
        this.movement.backwards();
        this.movement.turnLeft();


        ArrayList<String> expected = new ArrayList<>();
        expected.add("mv forward");
        expected.add("mv forward");
        expected.add("mv forward");
        expected.add("mv right");
        expected.add("mv right");
        expected.add("mv backwards");
        expected.add("mv left");

        this.movement.runTest(expected);
    }
}
