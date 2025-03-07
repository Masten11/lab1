import java.awt.*;
import java.util.Scanner;


public abstract class Car extends Veichle {
    private int nrDoors;          // Number of doors on the car (private)
    private Color color;          // Color of the car (private)
    private String modelName;     // The car model name (private)
    private double length;  // The current speed of the car (private)
    private double width;  // The current speed of the car (private)

    // Constructor to initialize the car's attributes
    protected Car(int nrDoors, double enginePower, Color color, String modelName, double length, double width) {
        super(enginePower);
        this.nrDoors = nrDoors;
        this.color = color;
        this.modelName = modelName;
        this.length = length;
        this.width = width;
    }


    // Public getter for nrDoors
    public int getNrDoors() {
        return nrDoors;
    }

    // Public getter for modelname
    public String getModelName() {
        return modelName;
    }


    // Public getter and setter for color
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

}
