package test.facade;

import interfaces.hardware.Movement;

import java.util.ArrayList;

public class FakeMovement implements Movement {
    private UnitTest test;

    public FakeMovement() {
        this.test = new UnitTest(this.getClass().getSimpleName());
    }

    public void forward() {
        this.test.pushResult("mv forward");
    }

    public void backward() {
        this.test.pushResult("mv backwards");
    }

    public void turnLeft() {
        this.test.pushResult("mv left");
    }

    public void turnRight() {
        this.test.pushResult("mv right");
    }

    public void runTest(ArrayList<String> expected) {
        this.test.displayResults(expected);
    }

    public void clearTest() {
        this.test.clearTest();
    }
}
