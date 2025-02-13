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

    public double getX() {
        return x;
    }

    public double getY() {
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
        currentSpeed = 0.1;
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

    public void incrementSpeed(double amount){
        // speed cant accede enginepower
        this.currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower());
    }

    public void decrementSpeed(double amount){
        // speed cant be below 0
        this.currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }


    public void gas(double amount){
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("Amount must be between 0 and 1");
        }
        incrementSpeed(amount);
    }

    // TODO fix this method according to lab pm
    public void brake(double amount){
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("Amount must be between 0 and 1");
        }
        decrementSpeed(amount);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What car would you like? (Saab/Volvo): ");
        String carChoice = scanner.nextLine().trim().toLowerCase();

        Car car = null;

        if (carChoice.equals("saab")) {
            car = new Saab95();
            car.startEngine();
            System.out.println("You chose a Saab.");
        } else if (carChoice.equals("volvo")) {
            car = new Volvo240();
            car.startEngine();
            System.out.println("You chose a Volvo.");
        } else {
            System.out.println("Invalid choice. Please choose 'Saab' or 'Volvo'.");
            return;
        }


        System.out.println("Engine started. Current speed: " + car.getCurrentSpeed());

        if (car instanceof Saab95) {
            ((Saab95) car).setTurboOn();
            System.out.println("Turbo turned on.");
        }

        car.gas(0.5); // Gas pedal at 50%
        System.out.println("Accelerating. Current speed: " + car.getCurrentSpeed());

        car.brake(0.2);
        System.out.println("Braking. Current speed: " + car.getCurrentSpeed());


        car.stopEngine();
        System.out.println("Engine stopped. Current speed: " + car.getCurrentSpeed());

        scanner.close();
    }


}
