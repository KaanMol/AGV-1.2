package vehicle;

import interfaces.Updatable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VehicleRoute {
    private ArrayList<String> intersection;

    public VehicleRoute() {
        this.intersection = new ArrayList<>();
    }

    public void createFile() {
        File routeFiles = new File("Routes.txt");
        try {
            if (routeFiles.createNewFile()) {
                System.out.println("File created: " + routeFiles.getName());
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace(); // not necessary
        }
    }

    public void writeFile() {
        try {
            FileWriter routeWriter = new FileWriter("Route.txt");
            for (String route : this.intersection) {
                routeWriter.write(route + System.lineSeparator());
            }
            routeWriter.close();
        } catch (IOException e) {
            e.printStackTrace(); // not necessary
        }
    }

    public String readFile() {
        try {
            File readRoute = new File("Route.txt");
            Scanner myReader = new Scanner(readRoute);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // not necessary
        }
        return "";
    }
}
