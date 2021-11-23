package test.facade;

import java.util.ArrayList;

public class UnitTest {
    private ArrayList<String> results = new ArrayList<>();
    private String className;

    private static final String ColorReset = "\033[0m";
    private static final String ColorRed = "\033[1;31m";
    private static final String ColorGreen = "\033[1;32m";
    private static final String ColorBlue = "\033[1;34m";
    private static final String ColorPurple = "\033[0;35m";
    private static final String ColorYellow = "\033[0;33m";

    private static final String passedIcon = ColorGreen + "✔";
    private static final String failedIcon = ColorRed + "✖";

    public UnitTest(String className) {
        this.className = className;
    }

    public void pushResult(String result) {
        this.results.add(result);
    }

    public void displayResults(ArrayList<String> expected) {
        System.out.println("Test Results of " + this.className + "\n");
        for (int  i = 0; i < this.results.size(); i++) {
            String currentResult = this.results.get(i);
            String currentExpected = expected.get(i);
            boolean passed = false;

            if (currentResult.equals(currentExpected)) {
                passed = true;
            }

            System.out.println((passed ? passedIcon : failedIcon) + "\t" + currentResult + ColorReset);
        }
    }

    public void clearTest() {
        this.results.clear();
    }
}
