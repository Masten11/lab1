import java.awt.*;
import java.util.Scanner;


public abstract class Car implements Movable{
    private int nrDoors;          
    private double enginePower;   
    private Color color;          
    private String modelName;    
    private double currentSpeed;  

    // Constructor to initialize the car's attributes

    private double x; // Current x-coordinate
    private double y; // Current y-coordinate
    private double direction; // Angle of movement in degrees

    protected Car(int nrDoors, double enginePower, Color color, String modelName) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        startEngine();
    }

    public void move() {
        // Update position based on speed and direction
        x += getCurrentSpeed() * Math.cos(Math.toRadians(direction));
        y += getCurrentSpeed() * Math.sin(Math.toRadians(direction));
    }

    public void turnLeft() {
        direction -= 90; // Turn left by 90 degrees
    }

    public void turnRight() {
        direction += 90; // Turn right by 90 degrees
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

 
    public int getNrDoors() {
        return nrDoors;
    }

  
    public double getEnginePower() {
        return enginePower;
    }

 
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // Denna används endast för testningen
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    protected void startEngine() {
        currentSpeed = 0.1;
    }

    protected void stopEngine() {
        currentSpeed = 0;
    }

    protected abstract double speedFactor();

    public void setCurrentSpeed(double speed){
        if (speed >= 0 && speed <= 125){
            this.currentSpeed = speed;
        }
    }

    public void incrementSpeed(double amount){
        // speed cant accede enginepower
        this.currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower());
    }

    public void decrementSpeed(double amount){
        // speed cant be below 0
        this.currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }

    // TODO fix this method according to lab pm
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
            System.out.println("You chose a Saab.");
        } else if (carChoice.equals("volvo")) {
            car = new Volvo240();
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
