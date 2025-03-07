import java.awt.*;
import java.util.Scanner;

//Vehicle class for movement/position
public abstract class Veichle implements Movable {
    private double currentSpeed;  // The current speed of the car (private)
    private double x; // Current x-coordinate
    private double y; // Current y-coordinate
    private double direction; // Angle of movement in degrees
    private double enginePower;   // Engine power of the car (private)


    protected Veichle (double enginePower) {
        this.enginePower = enginePower;
    }


    public double getEnginePower() {
        return enginePower;
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

    // Public getter for current speed
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    protected void setX(double amount) {
        x = amount;
    }

    protected void setY(double amount) {
        y = amount;
    }

    protected void setDirection(double amount) {
        direction = amount;
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
