import java.awt.*;
import java.util.Scanner;


public abstract class Car implements Movable{
    private int nrDoors;          // Number of doors on the car (private)
    private double enginePower;   // Engine power of the car (private)
    private Color color;          // Color of the car (private)
    private String modelName;     // The car model name (private)
    private double currentSpeed;  // The current speed of the car (private)
    private double length;  // The current speed of the car (private)
    private double width;  // The current speed of the car (private)

    // Constructor to initialize the car's attributes

    private double x; // Current x-coordinate
    private double y; // Current y-coordinate
    private double direction; // Angle of movement in degrees

    protected Car(int nrDoors, double enginePower, Color color, String modelName, double length, double width) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        this.length = length;
        this.width = width;
    }

    //förflyttar bilen i x led eller y led baserat på direction
    public void move() {
        x += getCurrentSpeed() * Math.cos(Math.toRadians(direction));
        y += getCurrentSpeed() * Math.sin(Math.toRadians(direction));
    }

    public void turnLeft() {
        direction -= 90; // Förflyttning till Vänster med 90 grader
    }

    public void turnRight() {
        direction += 90; // Förflyttning till Höger med 90 grader
    }

    public double getDirection() {
        return direction;
    }

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }

    // Public getter for nrDoors
    public int getNrDoors() {
        return nrDoors;
    }

    // Public getter for modelname
    public String getModelName() {
        return modelName;
    }

    // Public getter for enginePower
    public double getEnginePower() {
        return enginePower;
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

    // Public getter for current speed
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    // Protected methods for subclasses to control the engine
    protected void startEngine() {
        if (currentSpeed==0) {
            currentSpeed = 0.1;
        }
    }

    protected void stopEngine() {
        currentSpeed = 0;
    }

    protected abstract double speedFactor();

    protected void setX(double amount) {
        x = amount;
    }

    protected void setY(double amount) {
        y = amount;
    }

    protected void setDirection(double amount) {
        direction = amount;
    }

    public void incrementSpeed(double amount){
        // speed cant accede enginepower
        if (currentSpeed !=0) {
            currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower());
        }

    }

    public void decrementSpeed(double amount){
        // speed cant be below 0
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }


    public void gas(double amount){
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("Amount must be between 0 and 1");
        }
        incrementSpeed(amount);
    }

    public void brake(double amount){
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("Amount must be between 0 and 1");
        }
        decrementSpeed(amount);
    }
}
